package com.example.domain


import com.example.data.models.FilterType
import com.example.data.models.Movie
import com.example.data.interfaces.MovieRepository
import javax.inject.Inject

class FetchMoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(
        filterType: FilterType,
        resetData: Boolean = false,
        page: Int = 1
    ): List<Movie> {
        return repository.fetchMovies(filterType, page, resetData)
    }
}