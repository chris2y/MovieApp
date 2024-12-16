package com.example.movieapp.ui.screens.detailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.local.FavoriteMovie
import com.example.movieapp.data.repository.FavoriteRepository
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
    private val movieRepository: MovieRepository,
    private val favoriteRepository: FavoriteRepository
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

    suspend fun checkIsFavorite(id: Int?): Boolean{
        return favoriteRepository.checkIsFavorite(id)
    }

    fun toggleFavorite(movie: FavoriteMovie, isFavorite: Boolean) = viewModelScope.launch {
        if (isFavorite) {
            favoriteRepository.removeFromFavorites(movie)
        } else {
            favoriteRepository.addToFavorites(movie)
        }
    }
}
