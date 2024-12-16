package com.example.movieapp


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapp.ui.components.BottomNavigationBar
import com.example.movieapp.ui.navigation.BottomNavigationItem
import com.example.movieapp.ui.navigation.Screens
import com.example.movieapp.ui.screens.detailScreen.DetailScreen
import com.example.movieapp.ui.screens.favoriteScreen.FavoriteScreen
import com.example.movieapp.ui.screens.homeScreen.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieApp() {
    val navController = rememberNavController()

    // Track the current route
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Define routes where bottom navigation should be shown
    val bottomNavRoutes = listOf(
        Screens.Home.route,
        Screens.Favorite.route
    )

    // Dynamically determine the selected index based on the current route
    val selectedItem = remember(currentRoute) {
        BottomNavigationItem().bottomNavigationItems().indexOfFirst {
            it.route == currentRoute
        }.coerceIn(0, BottomNavigationItem().bottomNavigationItems().size - 1)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            // Only show bottom navigation for specific routes
            if (currentRoute in bottomNavRoutes) {
                BottomNavigationBar(
                    navController = navController,
                    selectedIndex = selectedItem,
                    onItemSelected = { index ->
                        val selectedRoute = BottomNavigationItem().bottomNavigationItems()[index].route
                        navController.navigate(selectedRoute) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination
                            launchSingleTop = true
                            // Restore state when re-selecting a previously selected item
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screens.Home.route) {
                HomeScreen(
                    navController = navController
                )
            }
            composable(Screens.Favorite.route) {
                FavoriteScreen(navController = navController)
            }

            composable(
                "detail_screen/{movieId}",
                arguments = listOf(navArgument("movieId") { type = NavType.IntType })
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getInt("movieId")
                DetailScreen(movieId, navController)
            }
        }
    }
}