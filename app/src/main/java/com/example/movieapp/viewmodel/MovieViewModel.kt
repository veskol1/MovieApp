package com.example.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.Movie
import com.example.movieapp.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
        viewModelScope.launch {
            val movies = repository.fetchUpcomingMovies()

            if (movies.isNotEmpty()) {
                _uiState.update {
                    it.copy(
                        status = Status.SUCCESS,
                        moviesList = movies
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

        viewModelScope.launch {
            delay(1000L) //todo remove this
            val newMoviesList = repository.fetchUpcomingMovies(uiState.value.page)

            _uiState.update {
                it.copy(
                    status = Status.SUCCESS,
                    moviesList = newMoviesList,
                    isLoadingMore = false
                )
            }
        }
    }

}

data class MoviesUiState(
    val status: Status = Status.LOADING,
    var page: Int = 1,
    val moviesList: List<Movie> = emptyList(),
    val isLoadingMore: Boolean = false
)

enum class Status {
    LOADING,
    SUCCESS,
    ERROR
}
