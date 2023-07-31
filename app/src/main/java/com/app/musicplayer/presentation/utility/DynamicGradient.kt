package com.app.musicplayer.presentation.utility

import android.provider.ContactsContract.Profile
import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.animation.expandHorizontally
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.palette.graphics.Palette

private val defaultVibrantColor = Color(0xFF485563)
private val defaultMutedColor = Color(0xFF29323c)


@Composable
fun dynamicGradient(subjectUrl: String, darkGradient: Boolean = true): Brush {
    val context = LocalContext.current
    var vibrantColor: Color? by remember{ mutableStateOf(null) }
    var mutedColor: Color? by remember{ mutableStateOf(null) }
    val animatedColorVibrant by animateColorAsState(
        targetValue =
        if (vibrantColor != null && mutedColor != null) {
            vibrantColor!!
        }else{
            defaultVibrantColor
        },
        animationSpec = tween(
            durationMillis = 500,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )
    val animatedColorMuted by animateColorAsState(
        targetValue =
        if (vibrantColor != null && mutedColor != null) {
            mutedColor!!
        }else{
            defaultMutedColor
        },
        animationSpec = tween(
            durationMillis = 500,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )
    LaunchedEffect(Unit) {
        val bitmap = getBitmap(context, subjectUrl)
        val palette = Palette.from(bitmap).generate()
        if (darkGradient) {
            vibrantColor = palette.darkVibrantSwatch?.let { Color(it.rgb) }
            mutedColor = palette.darkMutedSwatch?.let { Color(it.rgb) }
            if(vibrantColor == null || mutedColor == null){
                vibrantColor = palette.vibrantSwatch?.let { Color(it.rgb) }
                mutedColor = Color.Black
            }
        } else {
            vibrantColor = palette.vibrantSwatch?.let { Color(it.rgb) }
            //change value of mutedColor to create gradient instead of plain color
            mutedColor = vibrantColor
        }
    }

    return Brush.linearGradient(listOf(animatedColorVibrant, animatedColorMuted))

}