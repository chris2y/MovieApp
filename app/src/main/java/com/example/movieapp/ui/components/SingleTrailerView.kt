package com.example.movieapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp
import com.example.movieapp.data.model.Video
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun SingleTrailerView(trailer: Video?) {
    if (trailer == null) {
        Box(modifier = Modifier.fillMaxWidth()) {
            androidx.compose.material3.Text(text = "No Trailer Available")
        }
    } else {
        AndroidView(
            factory = { context ->
                YouTubePlayerView(context).apply {
                    addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.loadVideo(trailer.key, 0f)
                        }
                    })
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // Adjust height as needed
        )
    }
}
