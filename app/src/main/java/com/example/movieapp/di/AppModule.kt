package com.example.movieapp.di

import com.example.movieapp.data.remote.MovieApiService
import com.example.movieapp.data.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMovieRepository(
        movieApiService: MovieApiService
    ): MovieRepository {
        return MovieRepository(movieApiService)
    }
}
