package com.app.musicplayer.presentation.navigation

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.musicplayer.data.featuredContent.FeaturedContentViewModel
import com.app.musicplayer.data.track.Album
import com.app.musicplayer.data.track.FetchTrackViewModel
import com.app.musicplayer.data.track.Track
import com.app.musicplayer.presentation.screen.home.HomeScreen
import com.app.musicplayer.presentation.screen.player.PlayerScreen
import com.app.musicplayer.presentation.screen.playlist.PlaylistScreen
import kotlin.random.Random

@Composable
fun Navigation(){
    val navController = rememberNavController()
    val trackViewModel: FetchTrackViewModel = hiltViewModel()
    val featuredContentViewModel: FeaturedContentViewModel = hiltViewModel()

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
                    PlayerScreen(navController = navController,
                        surfaceColor = Brush.linearGradient(),
                        track = track,
                        playlistID = track.albumId)
                }

            }

        }
    }
}

