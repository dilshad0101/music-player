package com.app.musicplayer.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
    primary = Color(0xFF1A1A1A),
    secondary = Color(0xFFDADADA),
    tertiary = Color.DarkGray,
    secondaryContainer = Color(0xFF1E4071)
)

private val LightColorPalette = lightColorScheme(
    primary = Color(0xFF1A1A1A),
    secondary = Color(0xFFDADADA),
    tertiary = Color.DarkGray,
    secondaryContainer = Color(0xFF1E4071)
)

@Composable
fun MusicPlayerTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}