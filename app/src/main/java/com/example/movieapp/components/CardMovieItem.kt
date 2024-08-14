package com.example.movieapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieapp.model.Movie

@Composable
fun CardMovieItem(item: Movie, onCardClick: (String) -> Unit) {
    Card(modifier = Modifier.size(200.dp).clickable {
        onCardClick(item.uniqueId)
    }) {  //todo add animation on addition
        Text(text = item.title)
    }
}