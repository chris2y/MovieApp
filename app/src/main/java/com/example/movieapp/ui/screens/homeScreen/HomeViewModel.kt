package com.example.movieapp.ui.screens.homeScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.Movie
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

    // New state for pagination loading
    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore: StateFlow<Boolean> = _isLoadingMore.asStateFlow()

    private var currentPage = 1
    private val _popularMovies = mutableListOf<Movie>()

    init {
        fetchAllMovies()
    }

    private fun fetchAllMovies() {
        viewModelScope.launch {
            _homeScreenState.value = MovieUiState.Loading
            try {
                val trendingMovies = movieRepository.getTrendingMovies()
                val popularMovies = movieRepository.getPopularMovies(currentPage)

                _popularMovies.addAll(popularMovies)
                _homeScreenState.value = MovieUiState.Success(
                    trending = trendingMovies.take(8),
                    popular = _popularMovies
                )
            } catch (e: Exception) {
                _homeScreenState.value = MovieUiState.Error("Failed to fetch movies: ${e.message}")
                Log.e("HomeViewModel", "Error fetching movies", e)
            }
        }
    }

    fun loadMoreMovies() {
        if (_isLoadingMore.value) return

        viewModelScope.launch {
            _isLoadingMore.value = true
            try {
                currentPage++
                val newMovies = movieRepository.getPopularMovies(currentPage)

                _popularMovies.addAll(newMovies)
                _homeScreenState.value = when (val currentState = _homeScreenState.value) {
                    is MovieUiState.Success -> currentState.copy(popular = _popularMovies)
                    else -> currentState
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error loading more movies", e)
            } finally {
                _isLoadingMore.value = false
            }
        }
    }
}