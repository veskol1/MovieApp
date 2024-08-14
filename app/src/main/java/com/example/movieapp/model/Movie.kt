package com.example.movieapp.model

import androidx.compose.runtime.Stable
import com.google.gson.annotations.SerializedName
import java.util.UUID

@Stable
data class MovieResponse(
    val results: List<Movie>
)

@Stable
data class Movie(
    val uniqueId: String = UUID.randomUUID().toString(),

    @SerializedName("original_title")
    val title: String = "",

    @SerializedName("poster_path")
    val image: String = "",

    @SerializedName("overview")
    val description: String = "",

    @SerializedName("release_date")
    val releaseDate: String = ""
)
