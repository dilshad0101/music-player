package com.app.musicplayer.presentation.utility

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.app.musicplayer.R
import kotlinx.coroutines.*
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun ImageLoader(url: String,
                modifier: Modifier = Modifier,
                contentDescription: String,
                quality: FilterQuality = FilterQuality.Low,
                ){

    AsyncImage(
        model = url,
        contentDescription = contentDescription,
        contentScale = ContentScale.Fit,
        modifier = modifier,
        filterQuality = quality,
        placeholder = painterResource(id = R.drawable.albumcover_placeholder),
        onLoading = {

        }
        )
}

suspend fun getBitmap(context: Context, link: String): Bitmap {
    return withContext(Dispatchers.IO) {
        try {
            val url = URL(link)
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

