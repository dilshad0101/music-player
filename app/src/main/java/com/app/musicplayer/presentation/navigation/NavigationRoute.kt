package com.app.musicplayer.presentation.navigation

import androidx.annotation.DrawableRes
import com.app.musicplayer.R

sealed class NavigationRoute(
    val route: String,
    @DrawableRes val iconOutlined : Int,
    @DrawableRes val iconFilled : Int
    ) {
    object HomeScreen : NavigationRoute(
        route = "Home Screen",
        iconOutlined = R.drawable.home,
        iconFilled = R.drawable.home_filled
        )
    object LikedSongScreen : NavigationRoute(
        "likedSongScreen",
        iconOutlined = R.drawable.heart,
        iconFilled = R.drawable.heart_filled
    )
    object PlaylistScreen : NavigationRoute(
        "screen2",
        iconOutlined = R.drawable.heart,
        iconFilled = R.drawable.heart_filled
    )
    object PlayerScreen : NavigationRoute(
        "screen3",
        iconOutlined = R.drawable.heart,
        iconFilled = R.drawable.heart

        )
    object SettingsScreen : NavigationRoute(
        "screen4",
        iconOutlined = R.drawable.person,
        iconFilled = R.drawable.personfilled
    )
    object SearchScreen : NavigationRoute(
        "screen5",
        iconOutlined = R.drawable.search,
        iconFilled = R.drawable.search_filled
    )
    object TasteEditorScreen: NavigationRoute(
        "screen6",
        iconOutlined = R.drawable.search,
        iconFilled = R.drawable.search_filled
    )
}


