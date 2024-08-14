package com.example.movieapp.repository

import com.example.movieapp.api.ApiService
import com.example.movieapp.constans.Constants.API_KEY
import com.example.movieapp.model.Movie
import com.example.movieapp.viewmodel.FilterType
import com.example.movieapp.viewmodel.FilterType.*
import javax.inject.Inject

class MovieRepository @Inject constructor(private val api: ApiService) {
    private var moviesList: MutableList<Movie> = arrayListOf()

    suspend fun fetchMovies(filterType: FilterType, page: Int = 1, resetData: Boolean = false): List<Movie> {
        if (resetData) {
            moviesList.clear()
        }
        try {
            val response = when (filterType) {
                UPCOMING -> {
                    api.getUpcomingMovies(apiKey = API_KEY, page = page)
                }
                TOP_RATED -> {
                    api.getTopRatedMovies(apiKey = API_KEY, page = page)
                }
                NOW_PLAYING -> {
                    api.getNowPlayingMovies(apiKey = API_KEY, page = page)
                }
            }

            if (response.isSuccessful && response.body() != null) {
                val fetchMoviesList = (response.body()?.results ?: arrayListOf()).toMutableList()
                moviesList.addAll(fetchMoviesList)
                return moviesList.distinctBy { it.id }
            }
        } catch (e: Exception) {
            return emptyList()
        }

        return emptyList()
    }

}