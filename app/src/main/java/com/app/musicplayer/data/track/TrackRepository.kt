package com.app.musicplayer.data.track

interface TrackRepository {
    suspend fun getTracks(): List<Album>
    suspend fun getTrack(id: Int): Track
    suspend fun requestTracks(key:String): MutableList<Track>
}
