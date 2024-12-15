package com.example.movieapp.ui.state

import com.example.movieapp.data.model.MovieFullDetails

sealed class MovieDetailUiState {
    object Loading : MovieDetailUiState()
    data class Success(val movieFullDetails: MovieFullDetails) : MovieDetailUiState()
    data class Error(val message: String) : MovieDetailUiState()
}


