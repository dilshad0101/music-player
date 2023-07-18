package com.app.musicplayer.di

import android.app.Application
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
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
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
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
@Module
@InstallIn(ViewModelComponent::class)
object VideoPlayerModule {

    @Provides
    @ViewModelScoped
    fun provideVideoPlayer(app: Application): Player {
        return ExoPlayer.Builder(app)
            .build()
    }
}