package com.example.movieapp.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapp.model.Movie
import com.example.movieapp.viewmodel.FilterType
import com.example.movieapp.viewmodel.MovieViewModel
import com.example.movieapp.viewmodel.Status

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    movieViewModel: MovieViewModel = viewModel(),
    navigateOnCardClick: (movieId: String) -> Unit = {}
) {
    val uiState by movieViewModel.uiState.collectAsStateWithLifecycle()

    when (uiState.status) {
        Status.SUCCESS -> {
            Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                DropdownMenu(
                    selectedFilterText = uiState.filterType.title,
                    filterOptions = FilterType.entries,
                    onFilterChange = { filterType ->
                        movieViewModel.onFilterChanged(filterType = filterType)
                    }
                )
                Box {
                    EndlessLazyVerticalGrid(
                        loading = uiState.isLoadingMore,
                        items = uiState.moviesList,
                        itemKey = Movie::id,
                        itemContent = { item: Movie -> CardMovieItem(movie = item, onCardClick = {
                            navigateOnCardClick(it)
                        }) },
                        loadingProgressIndicator = { ProgressIndicator() },
                        loadMore = { movieViewModel.loadMoreMovies() }
                    )
                }
            }
        }

        Status.LOADING -> {
            ProgressIndicator(alignment = Alignment.Center)
        }

        Status.ERROR -> {
            Box(modifier = modifier.fillMaxSize()) {
                Text(text = "Error loading data", modifier = Modifier.align(Alignment.Center) )
            }
        }

        Status.NO_CONNECTION -> {
            Box(modifier = modifier.fillMaxSize()) {
                Text(text = "No internet connection", modifier = Modifier.align(Alignment.Center) )
            }
        }
    }
}



@Composable
@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
fun MainScreenPreview() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        DropdownMenu(
            selectedFilterText = "Top Rated",
            filterOptions = FilterType.entries,
            onFilterChange = { filterType ->

            }
        )
        Box {
            EndlessLazyVerticalGrid(
                loading = true,
                items = listOf(Movie(id = "91"),Movie(id ="82"),Movie(id = "83"),Movie(id ="72"),Movie(id = "73"),Movie(id ="62"),Movie(id = "63"),Movie(id ="52"),Movie(id = "53"),Movie(id ="42"),Movie(id = "43"),Movie(id ="32"),Movie(id = "33"),Movie(id ="22"),Movie(id = "23")),
                itemKey = Movie::id,
                itemContent = { item: Movie -> CardMovieItem(movie = item, onCardClick = {}) },
                loadingProgressIndicator = { ProgressIndicator() },
                loadMore = {  }
            )
        }
    }


}
