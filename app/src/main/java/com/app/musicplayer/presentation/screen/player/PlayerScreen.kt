package com.app.musicplayer.presentation.screen.player

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.musicplayer.R
import com.app.musicplayer.data.track.Track
import com.app.musicplayer.presentation.navigation.NavigationRoute
import com.app.musicplayer.presentation.utility.ImageLoader

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PlayerScreen(
    navController: NavController,
    surfaceColor: Brush,
    track: Track,
    playlistID: String,
    onPlaybackStateChange:(Boolean) -> Unit,
    isPlaying: () -> Boolean,
    playbackPosition : () -> Long,
    onSeek: (Float) -> Unit,
    totalDuration: () -> Long
){
    var isLyricVisible by remember{ mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(surfaceColor)
    ) {
        LazyColumn(
            modifier = Modifier.padding(22.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ){
            this.item{
                IconButton(onClick = {
                    navController.navigate(NavigationRoute.PlaylistScreen.route+"/$playlistID") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.Start)
                    ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrowleft),
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                AnimatedContent(targetState = isLyricVisible) {state: Boolean ->
                    if (!state){
                        ImageLoader(
                            url = track.coverArtUrl,
                            contentDescription = "cover art of song " + track.title,
                            modifier = Modifier
                                .clip(RoundedCornerShape(10))
                                .clickable {
                                    isLyricVisible = true
                                }
                                .size(300.dp)

                            )
                    }else{
                        Text(text = "Lyrics HEHEHE")
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        track.title,
                        style = MaterialTheme.typography.displayLarge
                    )
                    Text(
                        track.artist,
                        style = MaterialTheme.typography.displaySmall
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    SliderBar(
                        playbackPosition = playbackPosition,
                        onValueChange = {value->
                            onSeek.invoke(value)
                        },
                        totalDuration = totalDuration
                        )
                    Spacer(modifier = Modifier.height(32.dp))

                    PlayerControllerRow(
                        onPlaybackStateChange = { onPlaybackStateChange.invoke(it) },
                        isPlaying = isPlaying
                    )
                }
            }
        }
    }
}
