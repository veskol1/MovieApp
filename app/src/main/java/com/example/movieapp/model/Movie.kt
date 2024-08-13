package com.example.movieapp.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val results: List<Movie>
)


data class Movie(
    val id: String = "",

    @SerializedName("original_title")
    val title: String = "",

    @SerializedName("poster_path")
    val image: String = "",

    @SerializedName("overview")
    val description: String = "",

    @SerializedName("release_date")
    val releaseDate: String = ""
)
