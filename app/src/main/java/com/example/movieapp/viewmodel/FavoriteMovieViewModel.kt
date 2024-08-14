package com.example.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.Movie
import com.example.movieapp.repository.LocalMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMovieViewModel @Inject constructor(
    private val localRepository: LocalMovieRepository
) : ViewModel() {

    private val _favoriteMoviesUiState = MutableStateFlow<List<Movie>>(emptyList())
    var favoriteMoviesUiState = _favoriteMoviesUiState.asStateFlow()

    private fun findSavedMovie(movieId: String): Movie {
        return favoriteMoviesUiState.value.find { it.id == movieId }!!
    }

    fun removeFavoriteMovie(movieId: String) {
        val movie = findSavedMovie(movieId = movieId)
        viewModelScope.launch(Dispatchers.IO) {
            localRepository.deleteMovieToDb(movie)
            updateFavoriteMoviesState()
        }
    }

    fun updateFavoriteMoviesState() {
        viewModelScope.launch(Dispatchers.IO) {
            _favoriteMoviesUiState.update {
                localRepository.gelAllFavoriteMovies()
            }
        }
    }
}
