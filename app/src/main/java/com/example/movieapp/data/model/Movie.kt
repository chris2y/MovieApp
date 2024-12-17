package com.example.movieapp.data.model


import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,
    val title: String = "",
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("overview")
    val description: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("vote_average")
    val rating: Double
)

data class MovieResponse(
    val results: List<Movie>
)