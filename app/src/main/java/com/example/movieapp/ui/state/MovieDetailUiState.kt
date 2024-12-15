package com.example.movieapp.ui.state

import com.example.movieapp.data.model.Movie

sealed class MovieDetailUiState {
    object Initial : MovieDetailUiState()
    object Loading : MovieDetailUiState()
    data class Success(val movie: Movie) : MovieDetailUiState()
    data class Error(val message: String) : MovieDetailUiState()
}

