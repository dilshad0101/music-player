package com.app.musicplayer.presentation.screen.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.musicplayer.data.featuredContent.FeaturedContent
import com.app.musicplayer.data.track.Album
import com.app.musicplayer.presentation.navigation.BottomNavBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    featuredContents: List<FeaturedContent>,
    latestReleaseAlbums: List<Album>,
    personalizedPlaylists: List<Album>?,
    onAlbumClick: (Album) -> Unit
){
    val mockList = mutableListOf<Section>(
        Section(
            title = "Latest Release",
            albums = latestReleaseAlbums

        )
    )
    if (personalizedPlaylists != null){
        mockList.add(
            Section(
                title = "Just For You",
                albums = personalizedPlaylists
            )
        )
    }
    Scaffold(
        containerColor = MaterialTheme.colorScheme.primary,
        bottomBar = {
            Column(
                modifier = Modifier.background(
                    brush = Brush.verticalGradient(
                        0f to Color.Transparent,
                        Float.POSITIVE_INFINITY to MaterialTheme.colorScheme.primary
                    )
                )
            ){
                BottomNavBar(navController)
            }
        }
    ) {
        LazyColumn(modifier = Modifier.padding()) {
            this.item {
                Column(
                    modifier = Modifier
                        .background(brush = Brush.verticalGradient(
                            Float.NEGATIVE_INFINITY to Color(0xFF00275E),
                            Float.POSITIVE_INFINITY to Color.Transparent))
                ) {
                    HomeTopBar()
                }

                FeaturedContentCardRow(contentList = featuredContents)

                Column(){
                    mockList.forEach { section ->
                        AlbumRow(
                            title = section.title,
                            albumList = section.albums,
                            onClick = { onAlbumClick.invoke(it) }
                            )
                    }
                }
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}