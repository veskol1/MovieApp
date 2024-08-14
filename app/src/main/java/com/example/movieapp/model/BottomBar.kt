package com.example.movieapp.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBar(
    val route: String,
    val name: String,
    val icon : ImageVector,
    val selectedIcon : ImageVector
) {
    data object Movies: BottomBar(
        route = "movies",
        name = "Movies",
        icon = Icons.Outlined.PlayArrow,
        selectedIcon = Icons.Filled.PlayArrow
    )

    data object Favorites: BottomBar(
        route = "favorites",
        name = "Favorites",
        icon = Icons.Outlined.FavoriteBorder,
        selectedIcon = Icons.Filled.Favorite
    )

}