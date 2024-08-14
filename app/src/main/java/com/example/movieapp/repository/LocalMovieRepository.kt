package com.example.movieapp.repository

import com.example.movieapp.model.Movie
import com.example.movieapp.room.MovieDao
import javax.inject.Inject

class LocalMovieRepository @Inject constructor(private val movieDao: MovieDao) {

    fun checkIfMovieIsFavorite(movie: Movie): Boolean {
        return (movieDao.getAll().find { it.id == movie.id } != null)
    }

    fun insertMovieToDb(movie: Movie) {
        movieDao.insert(movie = movie)
    }

    fun deleteMovieToDb(movie: Movie) {
        movieDao.delete(movie = movie)
    }

    fun gelAllFavoriteMovies(): List<Movie> {
        return movieDao.getAll()
    }
}