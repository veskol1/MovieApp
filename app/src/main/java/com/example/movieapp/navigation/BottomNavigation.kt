package com.example.movieapp.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.movieapp.model.BottomBar


@Composable
fun BottomNavigation (navController: NavController) {
    val screens = listOf(BottomBar.Movies, BottomBar.Favorites)
    NavigationBar {
        screens.forEach { screen ->
            Item(item = screen, navController = navController)
        }
    }
}

@Composable
fun RowScope.Item(item: BottomBar, navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val selected = currentDestination?.route == item.route
    NavigationBarItem(
        icon = {
            Icon(item.selectedIcon.takeIf { selected } ?: item.icon , contentDescription = null)
        },
        label = { Text(item.name) },
        selected = selected,
        enabled = true,
        onClick = {
            navController.navigate(item.route) {
                popUpTo(BottomBar.Movies.route) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}