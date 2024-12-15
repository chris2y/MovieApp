package com.example.movieapp.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun RatingBar(
    rating: Double,
    modifier: Modifier = Modifier,
    maxRating: Int = 5,
    starSize: Dp = 22.dp,
    starPadding: Dp = 3.dp
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        for (i in 1..maxRating) {
            val icon = if (i <= rating.toInt()) {
                Icons.Default.Star // Full star
            } else if (i - rating < 1) {
                Icons.Default.StarHalf // Half star
            } else {
                Icons.Default.StarOutline // Empty star
            }

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Yellow,
                modifier = Modifier
                    .size(starSize)
                    .padding(horizontal = starPadding / 2)
            )
        }
    }
}
