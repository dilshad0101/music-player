package com.app.musicplayer.presentation.navigation

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.musicplayer.data.featuredContent.FeaturedContentViewModel
import com.app.musicplayer.data.player.MediaPlayerViewModel
import com.app.musicplayer.data.track.FetchTrackViewModel
import com.app.musicplayer.presentation.screen.home.HomeScreen
import com.app.musicplayer.presentation.screen.player.PlayerScreen
import com.app.musicplayer.presentation.screen.playlist.PlaylistScreen
import com.app.musicplayer.presentation.utility.dynamicGradient

@Composable
fun Navigation(){
    val navController = rememberNavController()
    val trackViewModel: FetchTrackViewModel = hiltViewModel()
    val featuredContentViewModel: FeaturedContentViewModel = hiltViewModel()
    val playerViewModel : MediaPlayerViewModel = hiltViewModel()

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
                    val gradient = dynamicGradient(subjectUrl = track.coverArtUrl)
                    playerViewModel.setTrack(Uri.parse(track.trackUrl))
                    PlayerScreen(navController = navController,
                        surfaceColor = gradient,
                        track = track,
                        playlistID = track.albumId,
                        onPlay = {
                            playerViewModel.play()
                        },
                        isPlaying = playerViewModel.isPlaying(),
                        onPause = {
                            playerViewModel.pause()
                        },
                        playbackPosition = playerViewModel.playbackPosition,
                        max = playerViewModel.maxDuration(),
                        onSeek = {
                            playerViewModel.onSeek(it)
                        }
                    )

                }else{
                    Box(
                        Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)) {
                        CircularProgressIndicator()
                    }
                }

            }

        }
    }
}

