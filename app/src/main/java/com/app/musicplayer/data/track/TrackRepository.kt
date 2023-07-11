package com.app.musicplayer.data.track

interface TrackRepository {
    suspend fun getTracks(): List<Track>
    suspend fun getTrack(id: Int): Track
}
