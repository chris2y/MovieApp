package com.example.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class CreditsResponse(
    val cast: List<Cast>,
    val crew: List<Crew>
)

data class Cast(
    val id: Int,
    val name: String,
    @SerializedName("profile_path")
    val profilePath: String?,
    val character: String
)

data class Crew(
    val id: Int,
    val name: String,
    @SerializedName("profile_path")
    val profilePath: String?,
    val job: String
)
