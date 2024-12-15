package com.example.movieapp.ui.screens.detailScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.movieapp.ui.state.MovieDetailUiState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun DetailScreen(
    movieId: Int?,
    navController: NavController,
    viewModel: DetailViewModel = hiltViewModel()
) {
    // Fetch movie details based on movieId
    LaunchedEffect(movieId) {
        movieId?.let {
            viewModel.fetchMovieDetailsWithExtras(it)
        }
    }

    val movieDetailState by viewModel.movieDetailState.collectAsStateWithLifecycle()

    when (val state = movieDetailState) {
        is MovieDetailUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is MovieDetailUiState.Success -> {
            // Display movie details
            Column {
                Text(text = state.movieFullDetails.cast.toString())
                // Add more details as needed
            }
        }
        is MovieDetailUiState.Error -> {
            Text(text = "Error: ${state.message}")
        }
        else -> {}
    }
}