package com.app.musicplayer.presentation.screen.player

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import com.app.musicplayer.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PlayerControllerRow(
    onPlaybackStateChange:(Boolean) -> Unit,
    isPlaying: Boolean
){
    var isPlayingState by remember{
        mutableStateOf(isPlaying)
    }
    val defaultModifier = Modifier


    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
        ) {
        IconButton(
            onClick = {

            },
            modifier = defaultModifier
        ) {
            Icon(
                painterResource(id = R.drawable.shuffle),
                contentDescription = "shuffle",
                tint = Color.White
            )
        }

        IconButton(
            onClick = {

            },
            modifier = defaultModifier
        ) {
            Icon(
                painterResource(id = R.drawable.previous),
                contentDescription = "previous",
                tint = Color.White
            )
        }

        IconButton(
            onClick = {
                if(!isPlayingState) {
                    onPlaybackStateChange.invoke(false)

                }else{
                    onPlaybackStateChange.invoke(true)
                }
                isPlayingState = !isPlayingState
            },
            modifier = Modifier.size(80.dp).background(Color.White, RoundedCornerShape(100)),
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = Color.Black
            )
        ) {
            AnimatedContent(
                targetState = isPlayingState
                ) {state->
                if (state){

                    Icon(
                        painterResource(id = R.drawable.pause),
                        contentDescription = "pause",
                        tint = Color.Black
                    )
                }else{
                    Icon(
                        painterResource(id = R.drawable.play),
                        contentDescription = "play",
                        tint = Color.Black,
                        modifier = Modifier.padding(start = 3.dp)
                    )
                }

            }

        }
        IconButton(
            onClick = {

            },
            modifier = defaultModifier
        ) {
            Icon(
                painterResource(id = R.drawable.next),
                contentDescription = "next",
                tint = Color.White
            )
        }
        IconButton(
            onClick = {

            },
            modifier = defaultModifier
        ) {
            Icon(
                painterResource(id = R.drawable.repeat),
                contentDescription = "repeat this track",
                tint = Color.White
            )
        }
    }

}

