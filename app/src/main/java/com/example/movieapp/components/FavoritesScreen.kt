package com.example.movieapp.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapp.viewmodel.MovieViewModel

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    movieViewModel: MovieViewModel = viewModel(),
    navigateOnCardClick: (movieId: String) -> Unit = {}
) {
    Box(modifier = modifier.fillMaxSize()) {
        Text(text = "Favorites Screen")
    }
}