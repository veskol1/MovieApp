package com.example.movieapp.model

import androidx.compose.runtime.Stable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.UUID

@Stable
data class MovieResponse(
    val results: List<Movie>
)

@Stable
@Entity(tableName = "favorite_movies_table")
data class Movie(
    @PrimaryKey
    val uniqueId: String = UUID.randomUUID().toString(),

    @SerializedName("original_title")
    val title: String = "",

    @SerializedName("poster_path")
    val image: String = "",

    @SerializedName("overview")
    val description: String = "",

    @SerializedName("release_date")
    val releaseDate: String = "",

    @SerializedName("vote_average")
    val rating: String = ""
)
