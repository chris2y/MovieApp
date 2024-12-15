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
    private val _homeScreenState = MutableStateFlow<MovieUiState>(MovieUiState.Loading)
    val homeScreenState: StateFlow<MovieUiState> = _homeScreenState.asStateFlow()

    init {
        fetchAllMovies()
    }

    private fun fetchAllMovies() {
        viewModelScope.launch {
            _homeScreenState.value = MovieUiState.Loading
            try {
                val trendingMovies = movieRepository.getTrendingMovies()
                val popularMovies = movieRepository.getPopularMovies()
                _homeScreenState.value = MovieUiState.Success(
                    trending = trendingMovies.take(8),
                    popular = popularMovies
                )
            } catch (e: Exception) {
                _homeScreenState.value = MovieUiState.Error("Failed to fetch movies: ${e.message}")
            }
        }
    }
}