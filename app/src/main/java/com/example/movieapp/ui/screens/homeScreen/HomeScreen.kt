package com.example.movieapp.ui.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieapp.data.model.Movie
import com.example.movieapp.ui.components.RatingBar
import com.example.movieapp.ui.state.MovieUiState
import com.example.movieapp.utils.Constants

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), navController: NavController) {
    val homeScreenState by viewModel.homeScreenState.collectAsStateWithLifecycle()
    val isLoadingMore by viewModel.isLoadingMore.collectAsStateWithLifecycle()


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
                popularMovies = state.popular,
                navController = navController,
                isLoadingMore = isLoadingMore,
                onLoadMore = { viewModel.loadMoreMovies() }

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
    popularMovies: List<Movie>,
    navController: NavController,
    isLoadingMore: Boolean,
    onLoadMore: () -> Unit
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
            SingleImageCarousel(trendingMovies,navController)
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
        this.MovieGrid(popularMovies, navController )

        if (isLoadingMore) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

    }
    listState.OnBottomReached {
        onLoadMore()
    }
}

fun LazyListScope.MovieGrid(movies: List<Movie>,navController: NavController) {
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
                        .padding(4.dp)
                        .clickable { navController.navigate("detail_screen/${movie.id}") },
                    elevation = CardDefaults.cardElevation(5.dp),
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

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 8.dp)
                        ) {
                            // Custom Rating Bar
                            RatingBar(
                                rating = movie.rating / 2, // Scale from 10 to 5
                                modifier = Modifier.padding(end = 4.dp)
                            )

                            // Numeric Rating
                            Text(
                                text = "${String.format("%.1f", movie.rating)}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun SingleImageCarousel(images: List<Movie>,navController: NavController) {
    val listState = rememberLazyListState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(380.dp)
            .padding(8.dp)
    ) {
        LazyRow(
            state = listState,
        ) {
            items(count = images.size) { index ->
                val movie = images[index]
                Card(
                    modifier = Modifier
                        .width(280.dp)
                        .padding(horizontal = 8.dp)
                        .clickable { navController.navigate("detail_screen/${movie.id}") },
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
                                    maxLines = 1,
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

@Composable
fun LazyListState.OnBottomReached(loadMore: () -> Unit) {
    val layoutInfo = remember { derivedStateOf { this.layoutInfo } }
    val shouldLoadMore = remember { derivedStateOf {
        val lastVisibleItemIndex = layoutInfo.value.visibleItemsInfo.lastOrNull()?.index ?: -1
        val totalItemsCount = layoutInfo.value.totalItemsCount

        lastVisibleItemIndex >= totalItemsCount - 1
    }}

    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }
            .collect { if (it) loadMore() }
    }
}

