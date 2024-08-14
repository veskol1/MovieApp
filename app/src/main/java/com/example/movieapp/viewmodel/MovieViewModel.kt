package com.example.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.Movie
import com.example.movieapp.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MoviesUiState())
    var uiState = _uiState.asStateFlow()

    private val _movieUiState = MutableStateFlow(MovieUiState())
    var movieUiState = _movieUiState.asStateFlow()

    private val _favoriteMoviesUiState = MutableStateFlow<List<Movie>>(emptyList())
    var favoriteMoviesUiState = _favoriteMoviesUiState.asStateFlow()

    init {
        getRemoteMovies()
    }

    private fun getRemoteMovies(resetData: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            val moviesList = repository.fetchMovies(filterType = uiState.value.filterType, resetData = resetData)

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

    fun loadMore() {
        _uiState.update {
            it.copy(
                page = it.page + 1,
                isLoadingMore = true
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            val newMoviesList = repository.fetchMovies(
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
        val movie = findMovie(movieId = movieId)
        viewModelScope.launch(Dispatchers.IO) {
            _movieUiState.update {
                it.copy(
                    movie = movie,
                    isFavorite = repository.checkIfMovieIsFavorite(movie)
                )
            }
        }
    }

    private fun findMovie(movieId: String): Movie {
        return uiState.value.moviesList.find { it.id == movieId }!!
    }

    private fun findSavedMovie(movieId: String): Movie {
        return favoriteMoviesUiState.value.find { it.id == movieId }!!
    }

    fun handleFavoriteClicked() {
        val movie = movieUiState.value.movie
        viewModelScope.launch(Dispatchers.IO) {
            if (movieUiState.value.isFavorite) {
                repository.deleteMovieToDb(movie)
            } else {
                repository.insertMovieToDb(movie)
            }

            _movieUiState.update {
                it.copy(
                    isFavorite = !movieUiState.value.isFavorite
                )
            }
        }
    }

    fun removeFavoriteMovie(movieId: String) {
        val movie= findSavedMovie(movieId = movieId)
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMovieToDb(movie)
            updateFavoriteMoviesState()
        }
    }

    fun updateFavoriteMoviesState() {
        viewModelScope.launch(Dispatchers.IO) {
            _favoriteMoviesUiState.update {
                repository.gelAllFavoriteMovies()
            }
        }
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
    ERROR
}

enum class FilterType(val title: String) {
    UPCOMING("Upcoming"),
    TOP_RATED("Top Rated"),
    NOW_PLAYING("Now Playing")
}
