package com.app.musicplayer.presentation.navigation

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.Player
import androidx.navigation.NavHostController
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
import dagger.hilt.android.internal.lifecycle.HiltViewModelFactory

@Composable
fun Navigation(
    context: Context,

){
    val navController = rememberNavController()
    val trackViewModel: FetchTrackViewModel = hiltViewModel()
    val playerViewModel : MediaPlayerViewModel = hiltViewModel()
    val featuredContentViewModel: FeaturedContentViewModel = hiltViewModel()
    var isPlaying by remember{ mutableStateOf(false) }
    val player = playerViewModel.player
    var totalDuration by remember{
        mutableStateOf(0L)
    }
    val track = playerViewModel.currentTrack.value
    val albumValue = playerViewModel.currentQueue.value

    LaunchedEffect(Unit){
        val listener = object : Player.Listener{
            override fun onEvents(player: Player, events: Player.Events) {
                super.onEvents(player, events)
                totalDuration = player.duration.coerceAtLeast(0L)
            }

        }
        player.addListener(listener)



    }

    LaunchedEffect(Unit){
        playerViewModel.updatePlaybackPosition()
    }
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.HomeScreen.route
    ){
        composable(NavigationRoute.HomeScreen.route){
            HomeScreen(
                navController = navController,
                featuredContents = featuredContentViewModel.featureContentList.value,
                albums = trackViewModel.getAlbum(),
                onAlbumClick = {
                    playerViewModel.currentQueue.value = it
                    navController.navigate(NavigationRoute.PlaylistScreen.route)
                }
            )
        }
        composable(
            NavigationRoute.PlaylistScreen.route,
            ){

            if (albumValue != null){
                PlaylistScreen(
                    playlist = albumValue,
                    navController = navController,
                    onClickInTrack = {
                        playerViewModel.setTrack(Uri.parse(it.trackUrl))
                        playerViewModel.currentTrack.value = it
                        navController.navigate(NavigationRoute.PlayerScreen.route)
                    }
                )
            }

        }
        composable(NavigationRoute.SearchScreen.route){

        }

        composable(NavigationRoute.PlayerScreen.route
//            )
        ){
            if (track!=null){
                val darkGradient = dynamicGradient(subjectUrl = track.coverArtUrl)
                val lightGradient = dynamicGradient(
                    subjectUrl = track.coverArtUrl,
                    darkGradient = false
                )
                PlayerScreen(navController = navController,
                    lightGradient = lightGradient,
                    darkGradient = darkGradient,
                    track = track,
                    playlistID = track.albumId,
                    onPlaybackStateChange = {attemptedToPause ->
                        if (attemptedToPause){
                            playerViewModel.pause()
                        }else{
                            playerViewModel.play()
                        }
                        isPlaying = isPlaying.not()
                    },
                    isPlaying = { isPlaying },
                    playbackPosition = { playerViewModel.playbackPosition.value },
                    totalDuration = { totalDuration },
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

