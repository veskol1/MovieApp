package com.example.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.example.data.Constants.API_KEY
import com.example.data.api.ApiService
import com.example.data.interfaces.MovieRepository
import com.example.data.models.FilterType.UPCOMING
import com.example.data.models.FilterType.TOP_RATED
import com.example.data.models.FilterType.NOW_PLAYING
import com.example.data.models.FilterType
import com.example.data.models.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val api: ApiService, private val dataStore: DataStore<Preferences>):
    MovieRepository {
    private var moviesList: MutableList<Movie> = arrayListOf()
    private val cacheTimeKey = longPreferencesKey("cache_image_time_key")

    override suspend fun fetchMovies(filterType: FilterType, page: Int, resetData: Boolean): List<Movie> {
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
                response.body()
                val fetchMoviesList = (response.body()?.results ?: arrayListOf()).toMutableList()
                moviesList.addAll(fetchMoviesList)
                return moviesList.distinctBy { it.id }
            }
        } catch (ignored: Exception) {
            return emptyList()
        }

        return emptyList()
    }

    override suspend fun saveCacheTimeToDataStore(time: Long) {
        dataStore.edit { preferences ->
            preferences[cacheTimeKey] = time
        }
    }

    override val savedCacheTime: Flow<Long> = dataStore.data.map { preferences ->
        preferences[cacheTimeKey] ?: 0
    }

}