package com.example.movieapp.ui.navigation

class MovieAppNavigation {
}

sealed class Screens(val route : String) {
    object Home : Screens("home_route")
    object Popular : Screens("popular_route")
}
