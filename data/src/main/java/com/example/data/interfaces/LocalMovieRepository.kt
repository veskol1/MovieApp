package com.example.data.interfaces

import com.example.data.models.Movie

interface LocalMovieRepository {
    fun checkIfMovieIsFavorite(movie: Movie): Boolean

    fun insertMovieToDb(movie: Movie)

    fun deleteMovieFromDb(movie: Movie)


    fun gelAllFavoriteMovies(): List<Movie>
}