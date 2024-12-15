package com.example.movieapp.ui.components

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.movieapp.ui.navigation.BottomNavigationItem

@Composable
fun BottomNavigationBar(navController: NavController, selectedIndex: Int, onItemSelected: (Int) -> Unit) {
    NavigationBar {
        BottomNavigationItem().bottomNavigationItems().forEachIndexed { index, navigationItem ->
            NavigationBarItem(
                selected = index == selectedIndex,
                label = {
                    Text(navigationItem.label)
                },
                icon = {
                    Icon(
                        navigationItem.icon,
                        contentDescription = navigationItem.label
                    )
                },
                onClick = {
                    onItemSelected(index)
                    navController.navigate(navigationItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
