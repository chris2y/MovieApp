package com.example.movieapp.data.repository



import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.remote.MovieApiService
import com.example.movieapp.utils.Constants
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApiService: MovieApiService
) {
    suspend fun getPopularMovies(): List<Movie> {
        return movieApiService.getPopularMovies(Constants.API_KEY).results
    }

    suspend fun getTrendingMovies(): List<Movie> {
        return movieApiService.getTrendingMovies(Constants.API_KEY).results
    }

}