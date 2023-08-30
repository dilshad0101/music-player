package com.app.musicplayer.data.featuredContent


interface FeaturedContentDataSource {
    suspend fun getContent(): List<FeaturedContent>
}