package com.app.musicplayer.data.featuredContent

import com.app.musicplayer.data.featuredContent.remote.FeaturedContentCurrentDataSource
import javax.inject.Inject

class DefaultFeaturedContentRepository @Inject constructor(
    private val featuredContentCurrentDataSource: FeaturedContentCurrentDataSource
): FeaturedContentRepository {
    override suspend fun getContent(): List<FeaturedContent> {
        return featuredContentCurrentDataSource.getContent()
    }
}