package com.app.musicplayer.presentation.utility

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.palette.graphics.Palette


private val defaultGradientColors = listOf(Color(0xFF485563), Color(0xFF29323c))

@Composable
fun dynamicGradient(subjectUrl: String, darkGradient: Boolean = true): Brush {
    val context = LocalContext.current
    var vibrantColor: Color? by remember { mutableStateOf(null) }
    var mutedColor: Color? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        val bitmap = getBitmap(context, subjectUrl)
        val palette = Palette.from(bitmap).generate()
        if (darkGradient) {
            vibrantColor = palette.darkVibrantSwatch?.let { Color(it.rgb) }
            mutedColor = palette.darkMutedSwatch?.let { Color(it.rgb) }
        } else {
            vibrantColor = palette.vibrantSwatch?.let { Color(it.rgb) }
            //change value of mutedColor to create gradient instead of plain color
            mutedColor = vibrantColor
        }
    }

    return if (vibrantColor != null && mutedColor != null) {
        Brush.linearGradient(listOf(vibrantColor!!, mutedColor!!))
    } else {
        Brush.linearGradient(defaultGradientColors)
    }
}