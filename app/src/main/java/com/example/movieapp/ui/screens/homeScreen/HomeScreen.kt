package com.example.movieapp.ui.screens.homeScreen

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieapp.data.model.Movie
import com.example.movieapp.ui.state.MovieUiState
import com.example.movieapp.utils.Constants

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val homeScreenState by viewModel.homeScreenState.collectAsStateWithLifecycle()

    when (val state = homeScreenState) {
        is MovieUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is MovieUiState.Success -> {
            HomeContent(
                trendingMovies = state.trending,
                popularMovies = state.popular
            )
        }
        is MovieUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun HomeContent(
    trendingMovies: List<Movie>,
    popularMovies: List<Movie>
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Text(
                text = "Trending Movies",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            )
        }
        // Trending Movies Carousel
        item {
            SingleImageCarousel(trendingMovies)
        }
        item {
            Text(
                text = "Movies",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
                    .fillMaxWidth()
            )
        }
        // Popular Movies Grid
        // Use 'this' to explicitly reference the LazyListScope
        this.MovieGrid(popularMovies)
    }
}

fun LazyListScope.MovieGrid(movies: List<Movie>) {
    itemsIndexed(movies.chunked(2)) { _, moviePair ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            moviePair.forEach { movie ->
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(0.6f)
                        .padding(4.dp),
                    elevation = CardDefaults.cardElevation(5.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(Constants.IMAGE_BASE_URL + movie.posterPath)
                                .crossfade(true)
                                .build(),
                            contentDescription = movie.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )

                        Text(
                            text = movie.title,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(1.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = "Rating: ${String.format("%.1f", movie.rating)}/10",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding( bottom = 8.dp)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun SingleImageCarousel(images: List<Movie>) {
    val listState = rememberLazyListState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(8.dp)
    ) {
        LazyRow(
            state = listState,
        ) {
            items(count = images.size) { index ->
                val movie = images[index]
                Card(
                    modifier = Modifier
                        .width(300.dp)
                        .padding(horizontal = 8.dp),
                    elevation = CardDefaults.cardElevation(5.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Movie Poster Image
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(Constants.IMAGE_BASE_URL + movie.posterPath)
                                .crossfade(true)
                                .build(),
                            contentDescription = movie.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        // Movie Details Overlay
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomStart)
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f))
                                    )
                                )
                                .padding(12.dp)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = movie.title,
                                    style = MaterialTheme.typography.headlineLarge.copy(
                                        color = Color.White,
                                        fontWeight = FontWeight.SemiBold
                                    ),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = movie.releaseDate,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = Color.LightGray
                                    ),
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}
