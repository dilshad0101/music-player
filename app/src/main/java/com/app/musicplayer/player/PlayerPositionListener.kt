package com.app.musicplayer.player

fun interface PlayerPositionListener {
    fun onPlayerPositionChanged(position: Long)
}