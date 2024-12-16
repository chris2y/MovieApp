package com.example.movieapp.ui.screens.favoriteScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.local.FavoriteMovie
import com.example.movieapp.data.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: FavoriteRepository
) : ViewModel() {
    val favorites: StateFlow<List<FavoriteMovie>> = repository.allFavorites
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun toggleFavorite(movie: FavoriteMovie, isFavorite: Boolean) = viewModelScope.launch {
        if (isFavorite) {
            repository.removeFromFavorites(movie)
        } else {
            repository.addToFavorites(movie)
        }
    }

    suspend fun checkIsFavorite(movieId: Int): Boolean {
        return repository.checkIsFavorite(movieId)
    }
}