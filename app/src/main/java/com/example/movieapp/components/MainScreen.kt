package com.example.movieapp.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
    val uiState by movieViewModel.uiState.collectAsState()

    when (uiState.status) {
        Status.SUCCESS -> {
            Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                DropdownMenu(
                    selectedFilterText = uiState.filterType.name,
                    filterOptions = FilterType.entries.map { it.name },
                    onFilterChange = { filterType ->
                        movieViewModel.onFilterChanged(filterType = filterType)
                    }
                )
                Box {
                    EndlessLazyVerticalGrid(
                        loading = uiState.isLoadingMore,
                        items = uiState.moviesList,
                        itemKey = Movie::uniqueId,
                        itemContent = { item: Movie -> CardMovieItem(movie = item, onCardClick = {
                            navigateOnCardClick(it)
                        }) },
                        loadingProgressIndicator = { ProgressIndicator(modifier = modifier) },
                        loadMore = { movieViewModel.loadMore() }
                    )
                }
            }
        }

        Status.LOADING -> {
            ProgressIndicator(modifier = modifier, alignment = Alignment.Center)
        }

        Status.ERROR -> {
            Box(modifier = modifier.fillMaxSize()) {
                Text(text = "Error loading data", modifier = modifier.align(Alignment.Center) )
            }
        }
    }
}



@Composable
@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
fun MainScreenPreview() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
       // DropdownMenuExample(sortFilterText = "TOP_RATED")
        Box {
            EndlessLazyVerticalGrid(
                loading = true,
                items = listOf(Movie(),Movie(),Movie(),Movie(),Movie(),Movie(),Movie()),
                itemKey = Movie::uniqueId,
                itemContent = { item: Movie -> CardMovieItem(movie = item, onCardClick = {}) },
                loadingProgressIndicator = { ProgressIndicator() },
                loadMore = {  }
            )

        }


    }
}
