package com.example.movieapp.ui.screens.detailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.ui.state.MovieDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _movieDetailState = MutableStateFlow<MovieDetailUiState>(MovieDetailUiState.Loading)
    val movieDetailState: StateFlow<MovieDetailUiState> = _movieDetailState.asStateFlow()

    // Cache to store fetched movie details
    private var cachedMovieId: Int? = null

    fun fetchMovieDetailsWithExtras(movieId: Int) {
        // Only fetch if the movie details haven't been loaded before
        if (cachedMovieId != movieId) {
            viewModelScope.launch {
                _movieDetailState.value = MovieDetailUiState.Loading
                try {
                    val movieDetails = movieRepository.getMovieDetailsWithExtras(movieId)
                    _movieDetailState.value = MovieDetailUiState.Success(movieDetails)
                    // Cache the movie ID to prevent re-fetching
                    cachedMovieId = movieId
                } catch (e: Exception) {
                    _movieDetailState.value = MovieDetailUiState.Error(e.message ?: "Unknown error")
                }
            }
        }
    }
}
