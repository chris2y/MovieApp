package com.example.movieapp.data.repository

import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.MovieFullDetails
import com.example.movieapp.data.remote.MovieApiService
import com.example.movieapp.utils.Constants
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApiService: MovieApiService
) {
    private var currentPopularPage = 1
    private val popularMoviesList = mutableListOf<Movie>()

    suspend fun getPopularMovies(page: Int): List<Movie> {
        val response = movieApiService.getPopularMovies(Constants.API_KEY, page)
        return response.results
    }

    // Optional method to maintain a cumulative list
    suspend fun loadMorePopularMovies(): List<Movie> {
        currentPopularPage++
        val newMovies = getPopularMovies(currentPopularPage)
        popularMoviesList.addAll(newMovies)
        return popularMoviesList
    }

    // Reset pagination if needed
    fun resetPopularMoviesPagination() {
        currentPopularPage = 1
        popularMoviesList.clear()
    }

    suspend fun getTrendingMovies(): List<Movie> {
        return movieApiService.getTrendingMovies(Constants.API_KEY).results
    }

    suspend fun getMovieDetails(movieId: Int): Movie {
        return movieApiService.getMovieDetails(movieId, Constants.API_KEY)
    }

    suspend fun getMovieDetailsWithExtras(movieId: Int): MovieFullDetails {
        val details = movieApiService.getMovieDetailsNew(movieId, Constants.API_KEY)
        val credits = movieApiService.getMovieCredits(movieId, Constants.API_KEY)
        val videos = movieApiService.getMovieVideos(movieId, Constants.API_KEY)

        return MovieFullDetails(
            details = details,
            cast = credits.cast,
            crew = credits.crew,
            trailers = videos.results.filter { it.type == "Trailer" && it.site == "YouTube" }
        )
    }

}
