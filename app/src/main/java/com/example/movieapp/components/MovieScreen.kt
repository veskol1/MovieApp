package com.example.movieapp.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.movieapp.model.Movie

@Composable
fun MovieScreen(modifier: Modifier = Modifier, movie: Movie) {
    Box(modifier = modifier.fillMaxSize()) {
        Text(text = "Movie Screen ${movie.title}")
    }
}