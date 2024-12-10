package com.example.movieapp.ui.state

import com.example.movieapp.data.model.Movie


sealed class MovieUiState {
    object Loading : MovieUiState()
    data class Success(val movies: List<Movie>) : MovieUiState()
    data class Error(val message: String) : MovieUiState()
}