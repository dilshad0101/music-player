package com.app.musicplayer.data.featuredContent

import com.app.musicplayer.data.featuredContent.remote.FeaturedContentCurrentDataSource

interface FeaturedContentDataSource {
    suspend fun getContent(): List<FeaturedContent>
}