package com.example.movieapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movieapp.data.model.Video

@Composable
fun TrailerSection(trailers: List<Video>) {
    val firstTrailer = trailers.firstOrNull()
    firstTrailer?.let { LazyYouTubePlayer(trailer = it) }
}


