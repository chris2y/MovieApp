package com.example.movieapp.ui.screens.favoriteScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieapp.data.local.FavoriteMovie
import com.example.movieapp.ui.components.RatingBar
import com.example.movieapp.utils.Constants

@Composable
fun FavoriteScreen(
    viewModel: FavoritesViewModel = hiltViewModel(),
    navController: NavController
) {
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()

    if (favorites.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No Favorite Movies",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(favorites.size) { index ->
                FavoriteMovieItem(
                    movie = favorites[index],
                    onMovieClick = { movieId ->
                        navController.navigate("detail_screen/$movieId")
                    }
                )
            }
        }
    }
}

@Composable
fun FavoriteMovieItem(
    movie: FavoriteMovie,
    onMovieClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onMovieClick(movie.id) },
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Movie Poster
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(Constants.IMAGE_BASE_URL + movie.posterPath)
                    .crossfade(true)
                    .build(),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(130.dp)
                    .aspectRatio(0.7f)
            )

            // Movie Details
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                movie.rating?.let { rating ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RatingBar(
                            rating = rating / 2, // Scale from 10 to 5
                            modifier = Modifier.padding(end = 4.dp)
                        )

                        Text(
                            text = "${String.format("%.1f", rating)}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                movie.overview?.let { overview ->
                    Text(
                        text = overview,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                movie.releaseDate?.let { releaseDate ->
                    Text(
                        text = "Release Date: $releaseDate",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}