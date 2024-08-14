package com.example.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.navigation.BottomNavigation
import com.example.movieapp.navigation.NavigationGraph
import com.example.movieapp.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieAppTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigation(navController = navController)
                    }) { innerPadding ->
                    NavigationGraph(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController
                    )
                }
            }
        }
    }
}

