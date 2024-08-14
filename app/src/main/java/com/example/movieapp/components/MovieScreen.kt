package com.example.movieapp.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.movieapp.constans.Constants.BASE_IMAGE_URL
import com.example.movieapp.model.Movie

@Composable
fun MovieScreen(
    modifier: Modifier = Modifier,
    movie: Movie,
    isFavorite: Boolean = false,
    onSaveMovieClicked: (movieId: String, isFavorite: Boolean) -> Unit = { _, _ -> }
) {
    var isFavoriteMovie by rememberSaveable { mutableStateOf(isFavorite) }
    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
        AsyncImage(
            modifier = Modifier.height(450.dp),
            model = BASE_IMAGE_URL + movie.image,
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
        )

        Text(modifier = Modifier.padding(20.dp), text = movie.title, fontWeight = FontWeight.Bold, fontSize = 26.sp, maxLines = 3)

        Row(modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()) {
            Column {
                Text(text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black)) {
                        append("User Rating: ")
                    }
                    append(movie.rating)
                })

                Text(modifier = Modifier.padding(top = 6.dp), text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black)) {
                        append("Release Date: ")
                    }
                    append(movie.releaseDate)
                })
            }

            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Outlined.Favorite.takeIf { isFavoriteMovie } ?: Icons.Outlined.FavoriteBorder,
                tint = Color.Red,
                contentDescription = "Image Favorite",
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        isFavoriteMovie = !isFavoriteMovie
                        onSaveMovieClicked(movie.uniqueId, isFavoriteMovie)
                    }
            )  //todo onclick change
        }


    }
}

@Composable
@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
fun MovieScreenPreview() {
    MovieScreen(
        movie = Movie(
            image = "https://image.tmdb.org/t/p/w200/ymbECZscR8BTkdvXziSinMIckAz.jpg",
            title = "vesko movie very long name! yes eseerer",
            rating = "7.44",
            releaseDate = "2024-05-30"
        ),
        isFavorite = false
    )
}