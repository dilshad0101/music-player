package com.app.musicplayer.data.track

data class Track(
    val id: String,
    val title: String,
    val artist: String,
    val album: String,
    val coverArtUrl: String,
    val trackUrl: String,
    val lyricUrl: String?,
    val albumId: String
)

data class Album(
    val id: String,
    val title: String,
    val artist: String,
    val tracks: List<Track>,
    val coverArtUrl: String
)