package com.app.musicplayer.presentation.utility

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun ImageLoader(url: String,
                modifier: Modifier = Modifier,
                contentDescription: String,
                quality: FilterQuality = FilterQuality.High
                ){
    AsyncImage(
        model = url,
        contentDescription = contentDescription,
        contentScale = ContentScale.Fit,
        modifier = modifier,
        filterQuality = quality
        )
}
