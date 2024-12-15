package com.example.movieapp.ui.navigation


sealed class Screens(val route: String) {
    object Home : Screens("home_route")
    object Popular : Screens("popular_route")
    object Detail : Screens("detail_route/{movieId}")

    // Method to create routes with arguments
    fun withArgs(vararg args: Any): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
