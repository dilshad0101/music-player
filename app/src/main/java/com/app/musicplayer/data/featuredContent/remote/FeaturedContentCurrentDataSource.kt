package com.app.musicplayer.data.featuredContent.remote

import com.app.musicplayer.data.featuredContent.FeaturedContent
import com.app.musicplayer.data.featuredContent.FeaturedContentDataSource
import javax.inject.Inject

class FeaturedContentCurrentDataSource @Inject constructor(): FeaturedContentDataSource {
    override suspend fun getContent(): List<FeaturedContent> {
        return listOf(
            FeaturedContent(
                index = 1,
                redirect = "",
                imageUrl = "https://e1.pxfuel.com/desktop-wallpaper/218/1018/desktop-wallpaper-nothing-was-the-same-drake-album-cover.jpg"
            ),FeaturedContent(
                index = 1,
                redirect = "",
                imageUrl = "https://e1.pxfuel.com/desktop-wallpaper/218/1018/desktop-wallpaper-nothing-was-the-same-drake-album-cover.jpg"
            ),FeaturedContent(
                index = 1,
                redirect = "",
                imageUrl = "https://e1.pxfuel.com/desktop-wallpaper/218/1018/desktop-wallpaper-nothing-was-the-same-drake-album-cover.jpg"
            ),
        )
    }
}