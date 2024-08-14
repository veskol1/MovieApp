package com.example.movieapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.movieapp.constans.Constants.BASE_IMAGE_URL_LIST
import com.example.movieapp.model.Movie
import com.example.movieapp.viewmodel.FavoriteMovieViewModel
import com.example.movieapp.viewmodel.MovieViewModel

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    favoriteMovieViewModel: FavoriteMovieViewModel,
    navigateOnCardClick: (movieId: String) -> Unit = {},
    removeFavoriteClick: (movieId: String) -> Unit = {}

) {
    val favoriteMovies by favoriteMovieViewModel.favoriteMoviesUiState.collectAsState()  //todo fix to lifecycle

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(items = favoriteMovies, key = { it.id }) { favoriteMovie ->
                FavoriteItem(
                    favoriteMovie = favoriteMovie,
                    navigateOnCardClick = navigateOnCardClick,
                    removeFavoriteClick = removeFavoriteClick
                )
            }
        }
    }
}

@Composable
fun FavoriteItem(
    favoriteMovie: Movie,
    navigateOnCardClick: (movieId: String) -> Unit = {},
    removeFavoriteClick: (movieId: String) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth()
            .clickable {
                navigateOnCardClick(favoriteMovie.id)
            }
    ) {
        Row(modifier = Modifier.padding(0.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = favoriteMovie.title,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(2f)
            )

            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .weight(1f)
                    .clickable {
                        removeFavoriteClick(favoriteMovie.id)
                    },
                imageVector = Icons.Outlined.Favorite,
                contentDescription = null,
                tint = Color.Red,
            )

            AsyncImage(
                modifier = Modifier.width(100.dp),
                model = BASE_IMAGE_URL_LIST + favoriteMovie.image,
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
        }
    }
}


@Composable
@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
fun FavoritesScreenPreview() {
    FavoriteItem(
        favoriteMovie = Movie(
            title = "test sdf vesko ads ada sad sad sad",
            releaseDate = "1.1.1999",
            rating = "6.5"
        )
    )
}