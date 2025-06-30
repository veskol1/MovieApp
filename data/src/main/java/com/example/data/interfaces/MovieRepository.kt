package com.example.data.interfaces

import com.example.data.models.FilterType
import com.example.data.models.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun fetchMovies(
        filterType: FilterType,
        page: Int = 1,
        resetData: Boolean = false
    ): List<Movie>

    suspend fun saveCacheTimeToDataStore(time: Long)

    val savedCacheTime: Flow<Long>
}