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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.ui.components.BottomNavigationBar
import com.example.movieapp.ui.navigation.Screens
import com.example.movieapp.ui.screens.homeScreen.HomeScreen
import com.example.movieapp.ui.screens.popularScreen.PopularScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieApp() {
    val navController = rememberNavController()
    var selectedItem by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = androidx.compose.ui.Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                selectedIndex = selectedItem,
                onItemSelected = { selectedItem = it }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.Home.route,
            modifier = androidx.compose.ui.Modifier.padding(paddingValues)
        ) {
            composable(Screens.Home.route) {
                HomeScreen()
            }
            composable(Screens.Popular.route) {
                PopularScreen()
            }
        }
    }
}