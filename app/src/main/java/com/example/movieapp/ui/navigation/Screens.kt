package com.example.movieapp.ui.navigation


sealed class Screens(val route: String) {
    object Home : Screens("home_route")
    object Favorite : Screens("favorite_route")
    object Detail : Screens("detail_route/{movieId}")

}
