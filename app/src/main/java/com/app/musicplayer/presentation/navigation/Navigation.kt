package com.app.musicplayer.presentation.navigation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.musicplayer.data.featuredContent.FeaturedContentViewModel
import com.app.musicplayer.player.MediaPlayerViewModel
import com.app.musicplayer.data.track.FetchTrackViewModel
import com.app.musicplayer.presentation.boarding.TasteProfileEditorScreen
import com.app.musicplayer.presentation.screen.home.HomeScreen
import com.app.musicplayer.presentation.screen.player.PlayerScreen
import com.app.musicplayer.presentation.screen.playlist.PlaylistScreen
import com.app.musicplayer.presentation.utility.dynamicGradient

@Composable
fun Navigation(
    mediaController: MediaController,
    viewModelStoreOwner: ViewModelStoreOwner
) {

    val navController = rememberNavController()
    val trackViewModel: FetchTrackViewModel = hiltViewModel()
    val playerViewModel = ViewModelProvider(viewModelStoreOwner)[MediaPlayerViewModel::class.java]

    playerViewModel.installController(mediaController)

    val player = playerViewModel.player
    val featuredContentViewModel: FeaturedContentViewModel = hiltViewModel()
    var isPlaying by remember{ mutableStateOf(false) }
    val totalDuration = playerViewModel.totalDuration
    val track = playerViewModel.currentTrack.value
    val albumValue = playerViewModel.currentQueue.value


    NavHost(
        navController = navController,
        startDestination = NavigationRoute.TasteEditorScreen.route
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
            Log.wtf("ok1223",albumValue?.id)
            if (albumValue != null){
                PlaylistScreen(
                    playlist = albumValue,
                    navController = navController,
                    onClickInTrack = {
                        playerViewModel.currentTrack.value = it
                        playerViewModel.setQueue(albumValue)
                        playerViewModel.setTrack(it.id)
                        navController.navigate(NavigationRoute.PlayerScreen.route)
                    }
                )
            }

        }
        composable(NavigationRoute.TasteEditorScreen.route){
            TasteProfileEditorScreen(
                onSubmit = {
                    navController.navigate(NavigationRoute.HomeScreen.route)
                }
            )
        }
        composable(NavigationRoute.SearchScreen.route){

        }

        composable(NavigationRoute.PlayerScreen.route
        ){
            if (track!=null && albumValue !=null){
                LaunchedEffect(track){
                    playerViewModel.updatePlaybackPosition()
                    if (player != null) {
                        if (player.playbackState == Player.STATE_IDLE){
                            player.prepare()
                        }
                    }
                }

                val darkGradient = dynamicGradient(subjectUrl = albumValue.coverArtUrl)
                val lightGradient = dynamicGradient(
                    subjectUrl = albumValue.coverArtUrl,
                    darkGradient = false
                )
                PlayerScreen(navController = navController,
                    lightGradient = lightGradient,
                    darkGradient = darkGradient,
                    track = track,
                    playlistID = track.albumId,
                    albumCoverArt = albumValue.coverArtUrl,
                    onPlaybackStateChange = {attemptedToPause ->
                        Log.d("ok223","ATTEMPTED ")

                        if (attemptedToPause){
                            if (player != null) {
                                if (player.isPlaying){
                                    playerViewModel.pause()
                                }else{
                                    player.stop() }
                            }
                        }else{
                            Log.d("ok223","ATTEMPTED To PLay")
                            playerViewModel.play()
                        }
                        isPlaying = isPlaying.not()
                    },
                    isPlaying = { isPlaying },
                    playbackPosition = { playerViewModel.playbackPosition.value },
                    totalDuration = { totalDuration.value },
                    onSeek = {
                        playerViewModel.onSeek(it)
                    },
                    onNext = {
                        playerViewModel.moveToNext(track.id)
                    },
                    onPrevious = {
                        playerViewModel.moveToPrevious(track.id)
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

