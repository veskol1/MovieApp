package com.example.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.connectivity.ConnectivityObserver
import com.example.movieapp.connectivity.NetworkConnectivityManager
import com.example.movieapp.constans.Constants.TIME_TO_CLEAR_IMAGE_CACHE
import com.example.movieapp.model.Movie
import com.example.movieapp.repository.LocalMovieRepository
import com.example.movieapp.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val remoteMoviesRepository: MovieRepository,
    private val localRepository: LocalMovieRepository,
    private val connectivityManager: NetworkConnectivityManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(MoviesUiState())
    var uiState = _uiState.asStateFlow()

    private val _movieUiState = MutableStateFlow(MovieUiState())
    var movieUiState = _movieUiState.asStateFlow()

    private val _clearCache = MutableStateFlow(false)
    var clearCache = _clearCache.asStateFlow()

    init {
        getRemoteMovies()
        checkIfNeedToClearImageCache()
        handleInternetConnectionState()
    }

    private fun getRemoteMovies(resetData: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            val moviesList = remoteMoviesRepository.fetchMovies(filterType = uiState.value.filterType, resetData = resetData)

            if (moviesList.isNotEmpty()) {
                _uiState.update {
                    it.copy(
                        status = Status.SUCCESS,
                        moviesList = moviesList
                    )
                }
            } else {
                _uiState.value = MoviesUiState(status = Status.ERROR)
            }
        }
    }

    fun loadMoreMovies() {
        _uiState.update {
            it.copy(
                page = it.page + 1,
                isLoadingMore = true
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            val newMoviesList = remoteMoviesRepository.fetchMovies(
                filterType = uiState.value.filterType,
                page = uiState.value.page,
            )

            _uiState.update {
                it.copy(
                    status = Status.SUCCESS,
                    moviesList = newMoviesList,
                    isLoadingMore = false
                )
            }
        }
    }

    fun onFilterChanged(filterType: FilterType) {
        _uiState.update {
            it.copy(
                status = Status.LOADING,
                page = 1,
                filterType = filterType,
                moviesList = emptyList()
            )
        }

        getRemoteMovies(resetData = true)
    }

    fun initMovieScreenUi(movieId: String) {
        val foundMovie = uiState.value.moviesList.find { it.id == movieId }!!
        viewModelScope.launch(Dispatchers.IO) {
            _movieUiState.update {
                it.copy(
                    movie = foundMovie,
                    isFavorite = localRepository.checkIfMovieIsFavorite(foundMovie)
                )
            }
        }
    }

    fun handleFavoriteClicked() {
        val movie = movieUiState.value.movie
        viewModelScope.launch(Dispatchers.IO) {
            if (movieUiState.value.isFavorite) {
                localRepository.deleteMovieFromDb(movie)
            } else {
                localRepository.insertMovieToDb(movie)
            }

            _movieUiState.update {
                it.copy(
                    isFavorite = !movieUiState.value.isFavorite
                )
            }
        }
    }

    private fun checkIfNeedToClearImageCache() {
        viewModelScope.launch {
            remoteMoviesRepository.savedCacheTime.take(1).collect { savedTime ->
                val currentTime = System.currentTimeMillis()
                val difference = currentTime - savedTime

                if (difference > 0) { //cache time is reached
                    _clearCache.value = true
                    remoteMoviesRepository.saveCacheTimeToDataStore(currentTime + TIME_TO_CLEAR_IMAGE_CACHE)

                } else if (savedTime == 0L) { // init cache first time
                    remoteMoviesRepository.saveCacheTimeToDataStore(currentTime + TIME_TO_CLEAR_IMAGE_CACHE)
                }
            }
        }
    }

    private fun handleInternetConnectionState() {
        connectivityManager.observe().onEach { status ->
            when (status) {
                ConnectivityObserver.Status.Available -> {
                    if (uiState.value.moviesList.isEmpty()) {
                        getRemoteMovies()
                    } else {
                        _uiState.update {
                            it.copy(
                                status = Status.SUCCESS
                            )
                        }
                    }
                }
                ConnectivityObserver.Status.Lost -> {
                    _uiState.update {
                        it.copy(
                            status = Status.NO_CONNECTION
                        )
                    }
                }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }
}

data class MoviesUiState(
    val status: Status = Status.LOADING,
    var page: Int = 1,
    val moviesList: List<Movie> = emptyList(),
    val isLoadingMore: Boolean = false,
    val filterType: FilterType = FilterType.UPCOMING
)

data class MovieUiState(
    val movie: Movie = Movie(),
    val isFavorite: Boolean = false
)

enum class Status {
    LOADING,
    SUCCESS,
    ERROR,
    NO_CONNECTION
}

enum class FilterType(val title: String) {
    UPCOMING("Upcoming"),
    TOP_RATED("Top Rated"),
    NOW_PLAYING("Now Playing")
}
