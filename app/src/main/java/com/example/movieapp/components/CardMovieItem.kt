package com.example.movieapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movieapp.constans.Constants.BASE_IMAGE_URL_LIST
import com.example.movieapp.model.Movie

@Composable
fun CardMovieItem(movie: Movie, onCardClick: (String) -> Unit) {
    Card(modifier = Modifier.height(180.dp).clipToBounds().clickable {
        onCardClick(movie.uniqueId)
    }) {  //todo add animation on addition
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = BASE_IMAGE_URL_LIST + movie.image,
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
        )
    }
}