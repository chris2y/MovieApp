package com.example.movieapp.ui.screens.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.ui.state.MovieUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieUiState>(MovieUiState.Loading)
    val uiState: StateFlow<MovieUiState> = _uiState.asStateFlow()

    private val _trendingMoviesState = MutableStateFlow<MovieUiState>(MovieUiState.Loading)
    val trendingMoviesState: StateFlow<MovieUiState> = _trendingMoviesState.asStateFlow()

    init {
        fetchPopularMovies()
        fetchTrendingMovies() // Fetch trending movies on initialization
    }

    private fun fetchTrendingMovies() {
        viewModelScope.launch {
            try {
                val movies = movieRepository.getTrendingMovies()
                _trendingMoviesState.value = MovieUiState.Success(movies.take(5)) // Limit to 5 movies
            } catch (e: Exception) {
                _trendingMoviesState.value = MovieUiState.Error("Failed to fetch trending movies: ${e.message}")
            }
        }
    }


    private fun fetchPopularMovies() {
        viewModelScope.launch {
            try {
                val movies = movieRepository.getPopularMovies()
                _uiState.value = MovieUiState.Success(movies)
            } catch (e: Exception) {
                _uiState.value = MovieUiState.Error("Failed to fetch movies: ${e.message}")
            }
        }
    }
}