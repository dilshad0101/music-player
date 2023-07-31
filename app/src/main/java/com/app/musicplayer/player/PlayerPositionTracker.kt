package com.app.musicplayer.player

import android.os.Handler
import android.os.Looper
import androidx.media3.common.Player

const val MIN_UPDATE_INTERVAL_MS: Long = 200
const val MAX_UPDATE_INTERVAL_MS: Long = 1000

class PlayerPositionTracker {

    val handlerUpdatePosition = Handler(Looper.getMainLooper())
    val runnableUpdatePosition = Runnable { updatePosition() }

    var player: Player? = null
        set(value) {
            field = value

            updatePlayerEventListenerRegistration()
            updatePosition()
        }

    var playerPositionListener: PlayerPositionListener? = null
        set(value) {
            field = value

            updatePlayerEventListenerRegistration()
            updatePosition()
        }

    private val playerEventListener = object : Player.Listener {
        override fun onEvents(player: Player, events: Player.Events) {
            if (events.containsAny(
                    Player.EVENT_PLAYBACK_STATE_CHANGED,
                    Player.EVENT_PLAY_WHEN_READY_CHANGED,
                    Player.EVENT_IS_PLAYING_CHANGED,
                    Player.EVENT_AVAILABLE_COMMANDS_CHANGED,
                    Player.EVENT_POSITION_DISCONTINUITY,
                    Player.EVENT_TIMELINE_CHANGED,
                    Player.EVENT_AVAILABLE_COMMANDS_CHANGED
                )
            ) updatePosition(player)
        }
    }

    private fun updatePlayerEventListenerRegistration() = player?.let {
        if (playerPositionListener != null) it.addListener(playerEventListener)
        else it.removeListener(playerEventListener)
    }

    private fun updatePosition(player: Player? = this.player) {
        handlerUpdatePosition.removeCallbacks(runnableUpdatePosition)
        if (player == null) return

        val position = player.currentPosition
        playerPositionListener?.onPlayerPositionChanged(position)
        val playbackState = player.playbackState
        if (player.isPlaying)
            handlerUpdatePosition.postDelayed(
                runnableUpdatePosition,
                player.playbackParameters.speed.let { playbackSpeed ->
                    if (playbackSpeed > 0) ((1000 - position % 1000) / playbackSpeed).toLong()
                    else MAX_UPDATE_INTERVAL_MS
                }.coerceIn(MIN_UPDATE_INTERVAL_MS, MAX_UPDATE_INTERVAL_MS)
            )
        else if (playbackState != Player.STATE_ENDED && playbackState != Player.STATE_IDLE)
            handlerUpdatePosition.postDelayed(runnableUpdatePosition, MAX_UPDATE_INTERVAL_MS)
    }

    fun release() {
        handlerUpdatePosition.removeCallbacks(runnableUpdatePosition)
        player?.removeListener(playerEventListener)
        player = null
        playerPositionListener = null

    }
}