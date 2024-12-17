package com.example.movieapp.data.model

data class MovieFullDetails(
    val details: MovieDetailResponse,
    val cast: List<Cast>,
    val crew: List<Crew>,
    val trailers: List<Video>
)
