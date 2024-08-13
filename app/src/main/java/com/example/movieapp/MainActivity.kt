package com.example.movieapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapp.components.EndlessLazyVerticalGrid
import com.example.movieapp.model.Movie
import com.example.movieapp.ui.theme.MovieAppTheme
import com.example.movieapp.viewmodel.MovieViewModel
import com.example.movieapp.viewmodel.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier, movieViewModel: MovieViewModel = viewModel(), ) {
    val uiState by movieViewModel.uiState.collectAsState()

    when (uiState.status) {
        Status.SUCCESS -> {
            EndlessLazyVerticalGrid(
                modifier = modifier,
                loading = uiState.isLoadingMore,
                items = uiState.moviesList,
                itemKey = Movie::idUnique,
                itemContent = { item: Movie -> CardMovieItem(item = item) },
                loadingProgressIndicator = { ProgressIndicator(modifier = modifier) },
                loadMore = { movieViewModel.loadMore() }
            )
         }

        Status.LOADING -> {
            Text(text = "Loading")
        }

        Status.ERROR -> {
            Text(text = "Error")
        }
    }
}


@Composable
fun CardMovieItem(item: Movie) {
    Card(modifier = Modifier.size(200.dp)) {  //todo add animation on addition
        Text(text = item.title)
    }
}

@Composable
fun ProgressIndicator(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = modifier
                .width(64.dp)
                .align(Alignment.BottomCenter),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MovieAppTheme {
        //Greeting("Android")
    }
}


