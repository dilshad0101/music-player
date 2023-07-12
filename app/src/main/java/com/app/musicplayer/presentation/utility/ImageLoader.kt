package com.app.musicplayer.presentation.utility

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import com.app.musicplayer.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun ImageLoader(url: String,
                modifier: Modifier = Modifier,
                contentDescription: String,
                quality: FilterQuality = FilterQuality.High,
                ){
    val context = LocalContext.current
    AsyncImage(
        model = url,
        contentDescription = contentDescription,
        contentScale = ContentScale.Fit,
        modifier = modifier,
        filterQuality = quality,
        )
}

suspend fun getBitmap(context: Context, link: String): Bitmap {
    return withContext(Dispatchers.IO) {
        val url = URL(link)
        try {
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val inputStream = connection.inputStream
            BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            BitmapFactory.decodeResource(context.resources, R.drawable.musics)
        }
    }
}

