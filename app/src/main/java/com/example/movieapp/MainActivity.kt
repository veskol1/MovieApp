package com.example.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.imageLoader
import com.example.movieapp.navigation.BottomNavigation
import com.example.movieapp.navigation.NavigationGraph
import com.example.movieapp.ui.theme.MovieAppTheme
import com.example.movieapp.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalCoilApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieAppTheme {
                val navController = rememberNavController()
                val movieViewModel: MovieViewModel = viewModel()

                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigation(navController = navController)
                    }) { innerPadding ->
                    NavigationGraph(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        movieViewModel = movieViewModel
                    )
                }

                val clearImageCacheState by movieViewModel.clearCache.collectAsStateWithLifecycle()
                if (clearImageCacheState) {
                    imageLoader.diskCache?.clear()
                }
            }
        }
    }
}

