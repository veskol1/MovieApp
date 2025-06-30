package com.example.data.repository

import com.example.data.interfaces.LocalMovieRepository
import com.example.data.models.Movie
import com.example.data.room.MovieDao
import javax.inject.Inject

class LocalMovieRepositoryImpl @Inject constructor(private val movieDao: MovieDao): LocalMovieRepository {

    override fun checkIfMovieIsFavorite(movie: Movie): Boolean {
        return (movieDao.getAll().find { it.id == movie.id } != null)
    }

    override fun insertMovieToDb(movie: Movie) {
        movieDao.insert(movie = movie)
    }

    override fun deleteMovieFromDb(movie: Movie) {
        movieDao.delete(movie = movie)
    }

    override fun gelAllFavoriteMovies(): List<Movie> {
        return movieDao.getAll()
    }
}