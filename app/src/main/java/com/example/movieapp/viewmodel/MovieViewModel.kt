package com.example.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.Movie
import com.example.movieapp.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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

    init {
        getMovies()
    }

    private fun getMovies(resetData: Boolean = false) {
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
            delay(1000L) //todo remove this
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

        getMovies(resetData = true)
    }
}

data class MoviesUiState(
    val status: Status = Status.LOADING,
    var page: Int = 1,
    val moviesList: List<Movie> = emptyList(),
    val isLoadingMore: Boolean = false,
    val filterType: FilterType = FilterType.UPCOMING
)

enum class Status {
    LOADING,
    SUCCESS,
    ERROR
}

enum class FilterType {
    UPCOMING,
    TOP_RATED,
    NOW_PLAYING
}
