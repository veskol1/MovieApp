package com.example.movieapp.repository

import com.example.movieapp.api.ApiService
import com.example.movieapp.constans.Constants.API_KEY
import com.example.movieapp.model.Movie
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val api: ApiService
) {

    suspend fun fetchUpcomingMovies(): List<Movie> {
        val response = api.getUpcomingMovies(apiKey = API_KEY, page = 1)

        if (response.isSuccessful && response.body() != null) {
            return response.body()?.results ?: emptyList()
        }

        return emptyList()
    }


}