package com.example.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movieapp.model.BottomBar
import com.example.movieapp.components.FavoritesScreen
import com.example.movieapp.components.MainScreen
import com.example.movieapp.components.MovieScreen
import com.example.movieapp.viewmodel.MovieViewModel

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    movieViewModel: MovieViewModel = viewModel(),
) {
    NavHost(
        navController,
        startDestination = BottomBar.Movies.route
    ) {

        composable(route = BottomBar.Movies.route) {
            MainScreen(
                modifier = modifier,
                movieViewModel = movieViewModel,
                navigateOnCardClick = { movieId ->
                    movieViewModel.initMovieScreenUi(movieId)
                    navController.navigate("movie")
                },
            )
        }

        composable(route = BottomBar.Favorites.route) {
            FavoritesScreen(
                modifier = modifier,
                movieViewModel = movieViewModel,
                navigateOnCardClick = { movieId ->
                    navController.navigate("movie/$movieId")
                }
            )
        }

        composable(route = "movie") {
            MovieScreen(
                modifier = modifier,
                movieViewModel = movieViewModel,
                onFavoriteMovieClicked = { movieViewModel.handleFavoriteClicked() })
        }

    }
}