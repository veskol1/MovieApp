package com.example.movieapp.navigation

import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import com.example.movieapp.viewmodel.FavoriteMovieViewModel
import com.example.movieapp.viewmodel.MovieViewModel

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    movieViewModel: MovieViewModel = viewModel(),
    favoriteMovieViewModel: FavoriteMovieViewModel = viewModel(),
) {
    NavHost(
        navController,
        startDestination = BottomBar.Movies.route
    ) {

        composable(route = BottomBar.Movies.route, enterTransition = { scaleIn() }, exitTransition = { scaleOut() }) {
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
            favoriteMovieViewModel.updateFavoriteMoviesState()
            FavoritesScreen(
                modifier = modifier,
                favoriteMovieViewModel = favoriteMovieViewModel,
                navigateOnCardClick = { movieId ->
                    movieViewModel.initMovieScreenUi(movieId)
                    navController.navigate("movie")
                },
                removeFavoriteClick = { movieId ->
                    favoriteMovieViewModel.removeFavoriteMovie(movieId)
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