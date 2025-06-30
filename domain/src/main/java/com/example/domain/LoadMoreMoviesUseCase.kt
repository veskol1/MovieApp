package com.example.domain


import com.example.data.models.FilterType
import com.example.data.models.Movie
import com.example.data.interfaces.MovieRepository
import javax.inject.Inject

class LoadMoreMoviesUseCase @Inject constructor(
    private val remoteMoviesRepository: MovieRepository
) {
    suspend operator fun invoke(
        pageToLoad: Int,
        filterType: FilterType,
    ): List<Movie> {
        return remoteMoviesRepository.fetchMovies(
            filterType = filterType,
            page = pageToLoad,
        )
    }
}