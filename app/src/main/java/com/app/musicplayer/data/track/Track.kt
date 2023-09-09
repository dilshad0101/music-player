package com.app.musicplayer.data.track

data class Track(
    val id: String,
    val title: String,
    val artist: List<Artist>,
    val album: String,
    val trackUrl: String,
    val lyricUrl: String?,
    val albumId: String,
    val coverArtUrl: String?

)

data class Album(
    val id: String,
    val title: String,
    val artist: String,
    val tracks: List<Track>,
    val coverArtUrl: String
)

data class Artist(
    val id: String,
    val name: String
)