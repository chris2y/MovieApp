package com.example.movieapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movieapp.data.model.MovieFullDetails

@Composable
fun MovieDetailContent(movieDetails: MovieFullDetails) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Poster Image
        movieDetails.details.posterPath?.let { posterPath ->
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500$posterPath",
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Title and Rating
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = movieDetails.details.title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Rating: ${movieDetails.details.voteAverage}/10",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Overview
            Text(
                text = "Overview",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = movieDetails.details.overview,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Cast Section
        Text(
            text = "Cast",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )
        CastSection(cast = movieDetails.cast)

        // Trailer Section
        Text(
            text = "Trailers",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )
        TrailerSection(trailers = movieDetails.trailers)
    }
}
