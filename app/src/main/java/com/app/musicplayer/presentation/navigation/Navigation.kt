package com.app.musicplayer.presentation.navigation

import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.palette.graphics.Palette
import com.app.musicplayer.R
import com.app.musicplayer.data.featuredContent.FeaturedContentViewModel
import com.app.musicplayer.data.track.Album
import com.app.musicplayer.data.track.FetchTrackViewModel
import com.app.musicplayer.data.track.Track
import com.app.musicplayer.presentation.screen.home.HomeScreen
import com.app.musicplayer.presentation.screen.player.PlayerScreen
import com.app.musicplayer.presentation.screen.playlist.PlaylistScreen
import com.app.musicplayer.presentation.utility.getBitmap
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun Navigation(){
    val navController = rememberNavController()
    val trackViewModel: FetchTrackViewModel = hiltViewModel()
    val featuredContentViewModel: FeaturedContentViewModel = hiltViewModel()

    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = NavigationRoute.HomeScreen.route
    ){
        composable(NavigationRoute.HomeScreen.route){
            HomeScreen(
                navController = navController,
                featuredContents = featuredContentViewModel.featureContentList.value,
                albums = trackViewModel.getAlbum()
            )
        }
        composable(
            NavigationRoute.PlaylistScreen.route+"/{ID}",
            arguments = listOf(navArgument("ID") {type = NavType.StringType })
            ){backStackEntry ->
            val value = backStackEntry.arguments?.getString("ID")
            val playlist = trackViewModel.getAlbum(value)
            if (value != null && playlist.isNotEmpty()){
                PlaylistScreen(
                    playlist = playlist.first(),
                    navController = navController
                )
            }

        }
        composable(NavigationRoute.SearchScreen.route){

        }

        composable(NavigationRoute.PlayerScreen.route +"/{ID}",
            arguments = listOf(navArgument("ID") {type = NavType.StringType })
        ){ navBackStackEntry ->

            val value = navBackStackEntry.arguments?.getString("ID")
            if (value !=null){
                val track = trackViewModel.findTrack(value)
                if (track!=null){
                    val defaultBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.musics)
                    var darkVibrant: Color? by remember{ mutableStateOf(null) }
                    var darkMuted:Color? by remember{ mutableStateOf(null) }
                    LaunchedEffect(Unit) {
                        val bitmap = getBitmap(context, track.coverArtUrl) ?: defaultBitmap
                        val palette = Palette.from(bitmap).generate()
                        darkMuted = palette.darkMutedSwatch?.let { Color(it.rgb) }
                        darkVibrant = palette.darkVibrantSwatch?.let { Color(it.rgb) }
                    }

                    if(darkMuted != null && darkVibrant != null){
                        val brush = Brush.linearGradient(
                            listOf(darkVibrant!!,darkMuted!!)
                        )

                        PlayerScreen(navController = navController,
                            surfaceColor = brush,
                            track = track,
                            playlistID = track.albumId)
                    }else{
                        Box(Modifier.fillMaxSize().wrapContentSize(Alignment.Center)) {
                            CircularProgressIndicator()
                        }

                    }

                }else{
                    Box(Modifier.fillMaxSize().wrapContentSize(Alignment.Center)) {
                        CircularProgressIndicator()
                    }
                }

            }

        }
    }
}

