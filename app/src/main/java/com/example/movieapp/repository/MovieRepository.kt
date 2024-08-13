package com.example.movieapp.repository

import com.example.movieapp.api.ApiService
import com.example.movieapp.constans.Constants.API_KEY
import com.example.movieapp.model.Movie
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val api: ApiService
) {

    private var moviesList: MutableList<Movie> = arrayListOf()

    suspend fun fetchUpcomingMovies(page: Int = 1): List<Movie> {
        val response = api.getUpcomingMovies(apiKey = API_KEY, page = page)

        if (response.isSuccessful && response.body() != null) {
            val fetchMoviesList = (response.body()?.results ?: arrayListOf()).toMutableList()
            moviesList.addAll(fetchMoviesList)
            return moviesList
        }

        return emptyList()
    }

}