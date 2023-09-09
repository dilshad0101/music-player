package com.app.musicplayer.data.track

import com.app.musicplayer.data.track.remote.TrackRemoteTrackDataSource
import javax.inject.Inject

class DefaultTrackRepository @Inject constructor(
    private val remoteDataSource: TrackRemoteTrackDataSource
): TrackRepository {
    override suspend fun getTracks(): List<Album> {
        return remoteDataSource.getTracks()
    }
    override suspend fun getTrack(id: Int): Track {
        return remoteDataSource.getTrack(id)
    }

    override suspend fun requestTracks(key: String): MutableList<Track> {
        return remoteDataSource.requestTracks(key)
    }


}