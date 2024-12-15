package com.example.movieapp.ui.components
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp
import com.example.movieapp.data.model.Video
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun LazyYouTubePlayer(trailer: Video) {
    AndroidView(
        factory = { context ->
            YouTubePlayerView(context).apply {
                // Initialize YouTube Player with the video
                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.cueVideo(trailer.key, 0f) // Cue the video at 0 seconds without playing
                    }
                })
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // Adjust height as needed
    )
}
