package com.app.musicplayer.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.app.musicplayer.data.featuredContent.FeaturedContent
import com.app.musicplayer.data.track.Album
import com.app.musicplayer.presentation.navigation.BottomNavBar
import com.app.musicplayer.presentation.navigation.NavigationRoute

@Composable
fun HomeScreen(
    navController: NavController,
    featuredContents: List<FeaturedContent>,
    albums: List<Album>
){
    val mockList = listOf<Section>(
        Section(
            title = "Latest Release",
            albums = albums

        ),
        Section(
            title = "Recently Played",
            albums = listOf(
                Album("12121",
                    title = "Dark Lemo Tapes",
                    artist = "Drake, Lil Baby",
                    tracks = listOf(),
                    coverArtUrl = "https://www.theglobeandmail.com/resizer/qqWRUfhhRCGq_uGzVZgCdcwR_0U=/1200x0/filters:quality(80):format(jpeg)/arc-anglerfish-tgam-prod-tgam.s3.amazonaws.com/public/3YV2PTJAVFGCVJK5IC6RJYY6EA"
                ),
                Album("121298",
                    title = "Dark Lemo Tapes",
                    artist = "Drake, Lil Baby",
                    tracks = listOf(),
                    coverArtUrl = "https://images.squarespace-cdn.com/content/v1/53b6eb62e4b06e0feb2d8e86/1557342351849-4N9P5EJJCL0DJMDTP0YI/SamSpratt_Logic_ConfessionsOfADangerousMind_album_artwork.jpg"
                ),
                Album("5652",
                    title = "Dark Lemo Tapes",
                    artist = "Drake, Lil Baby",
                    tracks = listOf(),
                    coverArtUrl = "https://i.cbc.ca/1.4678126.1527269930!/fileImage/httpImage/image.jpg_gen/derivatives/original_1180/shawn-mendes.jpg"
                )
            )

        ),
        Section(
            title = "Made For You",
            albums = listOf(
                Album("899",
                    title = "Dark Lemo Tapes",
                    artist = "Drake, Lil Baby",
                    tracks = listOf(),
                    coverArtUrl = "https://www.theglobeandmail.com/resizer/qqWRUfhhRCGq_uGzVZgCdcwR_0U=/1200x0/filters:quality(80):format(jpeg)/arc-anglerfish-tgam-prod-tgam.s3.amazonaws.com/public/3YV2PTJAVFGCVJK5IC6RJYY6EA"
                ),
                Album("22",
                    title = "Dark Lemo Tapes",
                    artist = "Drake, Lil Baby",
                    tracks = listOf(),
                    coverArtUrl = "https://images.squarespace-cdn.com/content/v1/53b6eb62e4b06e0feb2d8e86/1557342351849-4N9P5EJJCL0DJMDTP0YI/SamSpratt_Logic_ConfessionsOfADangerousMind_album_artwork.jpg"
                ),
                Album("12",
                    title = "Dark Lemo Tapes",
                    artist = "Drake, Lil Baby",
                    tracks = listOf(),
                    coverArtUrl = "https://i.cbc.ca/1.4678126.1527269930!/fileImage/httpImage/image.jpg_gen/derivatives/original_1180/shawn-mendes.jpg"
                )
            )

        ),
    )

    Scaffold(
        containerColor = Color.Black,
        bottomBar = {
            Column(
                modifier = Modifier.background(
                    brush = Brush.verticalGradient(
                        0f to Color.Transparent,
                        Float.POSITIVE_INFINITY to Color(0xFC000000)
                    )
                )
            ){
                BottomNavBar(navController)
            }
        }
    ) {
        LazyColumn(modifier = Modifier.padding()) {
            this.item(){
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
                            onClick = {
                                navController.navigate(NavigationRoute.PlaylistScreen.route + "/${it}")
                            }
                            )
                    }
                }
            }
        }
    }
}