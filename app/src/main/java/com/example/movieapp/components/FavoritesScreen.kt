package com.example.movieapp.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.movieapp.constans.Constants.BASE_IMAGE_URL_LIST
import com.example.movieapp.model.Movie
import com.example.movieapp.viewmodel.FavoriteMovieViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    favoriteMovieViewModel: FavoriteMovieViewModel,
    navigateOnCardClick: (movieId: String) -> Unit = {},
    removeFavoriteClick: (movieId: String) -> Unit = {}

) {
    val favoriteMovies by favoriteMovieViewModel.favoriteMoviesUiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        LazyColumn() {
            items(items = favoriteMovies, key = { it.id }) { favoriteMovie ->
                FavoriteItem(
                    favoriteMovie = favoriteMovie,
                    navigateOnCardClick = navigateOnCardClick,
                    removeFavoriteClick = removeFavoriteClick,
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
    val scope = rememberCoroutineScope()
    val deletedItems = remember { mutableStateListOf<Movie>() }  //used only for animation
    AnimatedVisibility(visible = !deletedItems.contains(favoriteMovie),
        enter = expandVertically(),
        exit = shrinkVertically(animationSpec = tween(150))
    ) {
        Card(
            modifier = Modifier
                .height(120.dp)
                .padding(vertical = 8.dp)
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
                            scope.launch {
                                deletedItems.add(favoriteMovie)
                                delay(150) //this is not best practice, I assumed user can't leave screen fast enough, it's just for animation purpose :)
                                removeFavoriteClick(favoriteMovie.id)
                            }
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
}
