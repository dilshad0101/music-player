package com.app.musicplayer.presentation.screen.playlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.musicplayer.R
import com.app.musicplayer.data.track.Album
import com.app.musicplayer.data.track.Track
import com.app.musicplayer.presentation.navigation.BottomNavBar
import com.app.musicplayer.presentation.navigation.NavigationRoute
import com.app.musicplayer.presentation.theme.Spacing
import com.app.musicplayer.presentation.utility.ImageLoader
import okio.utf8Size

@Composable
fun PlaylistScreen(
    playlist: Album,
    navController: NavController,
    onClickInTrack: (Track) -> Unit
){

    Scaffold(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.secondary,
        bottomBar = {

            Column(
                modifier = Modifier.background(
                    brush = Brush.verticalGradient(
                        0f to Color.Transparent,
                        Float.POSITIVE_INFINITY to MaterialTheme.colorScheme.primary
                    )
                )
            ){
                BottomNavBar(navController)
            }

        }
    ) {
        Column(
            modifier = Modifier.padding(
                top = Spacing.ContainerSpacing.value,
                start = Spacing.ContainerSpacing.value,
                end = Spacing.ContainerSpacing.value
            )
        ) {
            LazyColumn() {
                this.item{
                    IconButton(onClick = { navController.navigate(NavigationRoute.HomeScreen.route) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrowleft),
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(18.dp),
                        modifier = Modifier
                            .height(130.dp)
                            .fillMaxWidth()
                            .padding(end = 40.dp)
                        ) {
                        ImageLoader(
                            url = playlist.coverArtUrl,
                            contentDescription = playlist.title,
                            modifier = Modifier
                                .height(130.dp)
                                .width(130.dp)
                                .clip(RoundedCornerShape(20.dp))
                            )
                        Column(
                            verticalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxHeight()) {
                            Column {
                                Text(
                                    text = playlist.title.take(20) + if(playlist.title.utf8Size() > 20){ "..." }else{ "" },
                                    style = MaterialTheme.typography.titleLarge,
                                    softWrap = true,
                                    minLines = if(playlist.title.utf8Size() > 8) 2 else 1,
                                    maxLines = 2
                                )
                                Text(
                                    text = playlist.artist,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.tertiary
                                )
                            }
                            FilledTonalButton(
                                shape = RoundedCornerShape(5.dp),
                                onClick = { /*TODO*/ },
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = MaterialTheme.colorScheme.secondary,
                                    containerColor = MaterialTheme.colorScheme.tertiary
                                    ),
                                modifier = Modifier
                                    .width(155.dp)
                                    .height(34.dp)
                            ) {
                                Text(
                                    text = "Follow",
                                    style = MaterialTheme.typography.titleSmall,
                                    )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(28.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = (playlist.tracks.size).toString()+" Songs",
                            style = MaterialTheme.typography.bodyMedium
                            )
                        Text(
                            text = "2 hr 40 min",
                            style = MaterialTheme.typography.bodySmall
                            )
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Column(
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        playlist.tracks.forEach {track ->
                            TrackCard(
                                track = track,
                                onClick = {clickedTrack ->
                                    onClickInTrack.invoke(clickedTrack)
                                },
                                coverArtUri = playlist.coverArtUrl
                                )
                        }
                    }
                }
            }
        }
    }

}