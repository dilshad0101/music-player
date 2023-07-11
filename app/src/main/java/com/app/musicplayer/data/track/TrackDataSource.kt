package com.app.musicplayer.data.track

interface TrackDataSource {
    suspend fun getTracks(): List<Track>
    suspend fun getTrack(id: Int): Track
}