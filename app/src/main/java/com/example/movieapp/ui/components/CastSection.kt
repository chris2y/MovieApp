package com.example.movieapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movieapp.data.model.Cast

@Composable
fun CastSection(cast: List<Cast>) {
    LazyRow(modifier = Modifier.padding(horizontal = 16.dp)) {

        items(count = cast.size) { index ->
            val actor = cast[index]
            Column(
                modifier = Modifier.padding(end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500${actor.profilePath}",
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = actor.name,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}
