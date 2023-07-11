package com.app.musicplayer.presentation.screen.home

import com.app.musicplayer.data.track.Album

data class Section(
    val title: String,
    val albums: List<Album>
)