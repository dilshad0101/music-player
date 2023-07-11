package com.app.musicplayer.data.featuredContent

interface FeaturedContentRepository {
    suspend fun getContent(): List<FeaturedContent>
}