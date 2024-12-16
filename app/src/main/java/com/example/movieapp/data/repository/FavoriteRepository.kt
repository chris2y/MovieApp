package com.example.movieapp.data.repository

import com.example.movieapp.data.local.FavoriteDao
import com.example.movieapp.data.local.FavoriteMovie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepository @Inject constructor(
    private val favoriteDao: FavoriteDao
) {
    val allFavorites: Flow<List<FavoriteMovie>> = favoriteDao.getAllFavorites()

    suspend fun addToFavorites(movie: FavoriteMovie) {
        favoriteDao.insertFavorite(movie)
    }

    suspend fun removeFromFavorites(movie: FavoriteMovie) {
        favoriteDao.deleteFavorite(movie)
    }

    suspend fun checkIsFavorite(movieId: Int?): Boolean {
        return favoriteDao.isFavorite(movieId)
    }
}