package com.app.musicplayer.di

import com.app.musicplayer.data.featuredContent.DefaultFeaturedContentRepository
import com.app.musicplayer.data.featuredContent.FeaturedContentDataSource
import com.app.musicplayer.data.featuredContent.FeaturedContentRepository
import com.app.musicplayer.data.featuredContent.remote.FeaturedContentCurrentDataSource
import com.app.musicplayer.data.track.DefaultTrackRepository
import com.app.musicplayer.data.track.TrackDataSource
import com.app.musicplayer.data.track.TrackRepository
import com.app.musicplayer.data.track.remote.TrackRemoteTrackDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindTrackRepository(repository: DefaultTrackRepository): TrackRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindNetworkDataSource(dataSource: TrackRemoteTrackDataSource): TrackDataSource
}

@Module
@InstallIn(SingletonComponent::class)
abstract class FeaturedContentRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindFeaturedContentRepository(repository: DefaultFeaturedContentRepository): FeaturedContentRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class FeatureContentDataSource {

    @Singleton
    @Binds
    abstract fun bindFeatureContentDataSource(dataSource: FeaturedContentCurrentDataSource): FeaturedContentDataSource
}