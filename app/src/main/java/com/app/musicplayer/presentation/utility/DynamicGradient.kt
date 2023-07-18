package com.app.musicplayer.presentation.utility

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.palette.graphics.Palette


@Composable
fun dynamicGradient(subjectUrl: String): Brush{
    val context = LocalContext.current
    var darkVibrant: Color? by remember{ mutableStateOf(null) }
    var darkMuted: Color? by remember{ mutableStateOf(null) }
    LaunchedEffect(Unit) {
        val bitmap = getBitmap(context, subjectUrl)
        val palette = Palette.from(bitmap).generate()
        darkMuted = palette.darkMutedSwatch?.let { Color(it.rgb) }
        darkVibrant = palette.darkVibrantSwatch?.let { Color(it.rgb) }
    }
    return if(darkMuted != null && darkVibrant != null){
        val brush = Brush.linearGradient(
            listOf(darkVibrant!!,darkMuted!!)
        )
        brush

    }else{
        val brush = Brush.linearGradient(
            listOf(Color(0xFF485563),Color(0xFF29323c))
        )
        brush
    }
}