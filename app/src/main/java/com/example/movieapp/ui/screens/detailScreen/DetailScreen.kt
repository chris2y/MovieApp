package com.example.movieapp.ui.screens.detailScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.movieapp.ui.state.MovieDetailUiState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movieapp.data.local.FavoriteMovie
import com.example.movieapp.ui.components.CastSection
import com.example.movieapp.ui.components.RatingBar
import com.example.movieapp.ui.components.YouTubePlayerView
import com.example.movieapp.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(
    movieId: Int?,
    navController: NavController,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val movieDetailState by viewModel.movieDetailState.collectAsStateWithLifecycle()
    var isFavorite by remember { mutableStateOf(false) }

    val context = LocalContext.current

    // Fetch movie details based on movieId
    LaunchedEffect(movieId) {
        movieId?.let {
            viewModel.fetchMovieDetailsWithExtras(it)
        }
        movieId.let { isFavorite = viewModel.checkIsFavorite(it)  }


    }

    when (val state = movieDetailState) {
        is MovieDetailUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is MovieDetailUiState.Success -> {
            val movieDetails = state.movieFullDetails
            // Make the content scrollable
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    // Box with Backdrop Image
                    Box(modifier = Modifier.fillMaxWidth()) {
                        // Display Backdrop Image
                        movieDetails.details.backdropPath?.let { backdropPath ->
                            AsyncImage(
                                model = Constants.IMAGE_BACK_DROP_URL + backdropPath,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(350.dp),
                                contentScale = ContentScale.Crop
                            )
                        }

                        // Overlay Poster on Backdrop with Shadow
                        movieDetails.details.posterPath?.let { posterPath ->
                            AsyncImage(
                                model = Constants.IMAGE_BASE_URL + posterPath,
                                contentDescription = null,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .height(250.dp)
                                    .align(Alignment.BottomCenter)
                                    .shadow(8.dp)  // Adding shadow here
                            )
                        }

                        // Add Back Button
                        IconButton(
                            onClick = { navController.navigateUp() },
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White,
                                modifier = Modifier.size(30.dp)
                            )
                        }

                        // Add Favorites Button
                        IconButton(
                            onClick = { val favoriteMovie = FavoriteMovie(
                                id = movieDetails.details.id,
                                title = movieDetails.details.title,
                                posterPath = movieDetails.details.posterPath,
                                releaseDate = movieDetails.details.releaseDate,
                                overview = movieDetails.details.overview,
                                rating = movieDetails.details.voteAverage
                            )
                                viewModel.toggleFavorite(favoriteMovie, isFavorite)
                                isFavorite = !isFavorite

                                Toast.makeText(
                                    context,
                                    if (isFavorite) "Added to Favorites" else "Removed from Favorites",
                                    Toast.LENGTH_SHORT
                                ).show()
                                      },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(12.dp)

                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Add to Favorites",
                                tint = if (isFavorite)Color.Red else Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                    // Movie Details
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = movieDetails.details.title,
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 2.dp)
                        )
                        val genreText = movieDetails.details.genres.joinToString(", ") { it.name }
                        Text(
                            text = genreText,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        Column(modifier = Modifier.fillMaxWidth()) {
                            // Titles for rating and release date
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Release Date",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.weight(1f)
                                )

                                // Spacer to center-align the rating
                                Box(
                                    modifier = Modifier.weight(1f),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Rating",
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.SemiBold,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }

                            // Values for rating and release date
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = movieDetails.details.releaseDate,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Normal,
                                    modifier = Modifier.weight(1f)
                                )

                                // Center-align the rating components
                                Box(
                                    modifier = Modifier.weight(1f),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        RatingBar(movieDetails.details.voteAverage / 2, modifier = Modifier)
                                        Spacer(modifier = Modifier.height(4.dp)) // Small spacing between the RatingBar and the text
                                        Text(
                                            text = "${"%.1f".format(movieDetails.details.voteAverage)}/10",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Normal,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                        // Overview Section
                        Text(
                            text = "Overview",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 8.dp, top = 8.dp)
                        )
                        Text(
                            text = movieDetails.details.overview,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )



                    }
                    // Cast Section
                    Text(
                        text = "Cast",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                    )
                    CastSection(cast = movieDetails.cast)
                    // Trailer Section
                    Text(
                        text = "Trailers",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                    )
                    YouTubePlayerView(trailers = movieDetails.trailers)
                }

            }
        }
        is MovieDetailUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Error: ${state.message}")
            }
        }
        else -> {}

    }
}

