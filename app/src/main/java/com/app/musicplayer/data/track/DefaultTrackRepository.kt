package com.app.musicplayer.data.track

import com.app.musicplayer.data.track.remote.TrackRemoteTrackDataSource
import javax.inject.Inject

class DefaultTrackRepository @Inject constructor(
    private val remoteDataSource: TrackRemoteTrackDataSource
): TrackRepository {
    override suspend fun getTracks(): List<Track> {
        return remoteDataSource.getTracks()
    }


    override suspend fun getTrack(id: Int): Track {
        return remoteDataSource.getTrack(911)
    }


}