package com.app.musicplayer.presentation.screen.player

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.musicplayer.R
import com.app.musicplayer.data.track.Track
import com.app.musicplayer.presentation.navigation.BottomNavBar
import com.app.musicplayer.presentation.navigation.NavigationRoute
import com.app.musicplayer.presentation.utility.ImageLoader

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PlayerScreen(
    navController: NavController,
    surfaceColor: Brush,
    track: Track,
    playlistID: String
){
    var isLyricVisible by remember{ mutableStateOf(false) }
    Scaffold(
        bottomBar = {
            Column(
                modifier = Modifier.background(
                    brush = Brush.verticalGradient(
                        0f to Color.Transparent,
                        Float.POSITIVE_INFINITY to Color(0xFC000000)
                    )
                )
            ){
                BottomNavBar(navController)
            }
        }

    ) {
        LazyColumn(modifier = Modifier.padding(22.dp)){
            this.item{
                IconButton(onClick = { navController.navigate(NavigationRoute.PlaylistScreen.route+"/$playlistID") }) {
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
                                .size(338.dp)
                                .clickable {
                                    isLyricVisible = true
                                }
                                .clip(RoundedCornerShape(20))
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
                        track.title,
                        style = MaterialTheme.typography.displaySmall
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Row(verticalAlignment = CenterVertically) {
                        IconButton(onClick = { /*TODO*/ }) {
                        }
                    }
                }
            }
        }
    }
}
