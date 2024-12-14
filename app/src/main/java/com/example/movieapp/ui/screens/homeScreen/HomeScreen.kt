package com.example.movieapp.ui.screens.homeScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
        // Trending Movies Carousel
        item {
            SingleImageCarousel(trendingMovies)
        }

        // Popular Movies Grid
        // Use 'this' to explicitly reference the LazyListScope
        this.MovieGrid(popularMovies)
    }
}

// LazyListScope extension function remains the same as in the previous example
fun LazyListScope.MovieGrid(movies: List<Movie>) {
    items(count = movies.size) { index ->
        val movie = movies[index]
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.6f)
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
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
                    modifier = Modifier.padding(1.dp)
                )
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.6f),
        elevation = CardDefaults.cardElevation(4.dp)
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
                modifier = Modifier.padding(1.dp)
            )
        }
    }
}

@Composable
fun SingleImageCarousel(images: List<Movie>) {
    val listState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        LazyRow(
            state = listState,
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(count = images.size) { index ->
                val movie = images[index]
                Card(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(Constants.IMAGE_BASE_URL + movie.posterPath)
                            .crossfade(true)
                            .build(),
                        contentDescription = movie.title,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}