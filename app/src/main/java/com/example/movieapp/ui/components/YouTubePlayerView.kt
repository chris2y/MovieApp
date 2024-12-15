package com.example.movieapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YouTubePlayerView(videoId: String) {
    AndroidView(
        factory = { context ->
            YouTubePlayerView(context).apply {
                /*getYouTubePlayerWhenReady { youTubePlayer ->
                    youTubePlayer.cueVideo(videoId, 0f)
                }*/
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}
