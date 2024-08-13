package com.example.movieapp.api


import com.example.movieapp.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response

interface ApiService {

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("api_key") apiKey: String, @Query("page") page: Int): Response<MovieResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") apiKey: String, @Query("page") page: Int): Response<MovieResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("api_key") apiKey: String, @Query("page") page: Int): Response<MovieResponse>

}