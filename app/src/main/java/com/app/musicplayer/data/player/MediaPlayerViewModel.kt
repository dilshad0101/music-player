package com.app.musicplayer.data.player

import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@HiltViewModel
class MediaPlayerViewModel @Inject constructor(
    val player : Player
) : ViewModel() {

    var playbackPosition = mutableStateOf<Long>(0)
    private val playerPositionTracker= PlayerPositionTracker()

    init {
        player.prepare()
        viewModelScope.launch {
            while (true){
                updatePosition()
                playbackPosition.value = player.currentPosition
                delay(1.seconds/20)
            }

        }
    }


    fun onSeek(newPosition:Float){
        Log.wtf("TAG121", "Seek: $newPosition Total: ${player.duration.toFloat()}" )
        player.seekTo(newPosition.toLong())
    }
    fun updatePosition(player: Player = this.player) {
        val handlerUpdatePosition = playerPositionTracker.handlerUpdatePosition
        val runnableUpdatePosition = playerPositionTracker.runnableUpdatePosition
        val playerPositionListener = playerPositionTracker.playerPositionListener
        handlerUpdatePosition.removeCallbacks(runnableUpdatePosition)

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
    fun setTrack(uri: Uri){
        player.setMediaItem(MediaItem.fromUri(uri))
    }
    fun play(){
        player.play()
    }

    fun pause(){
        player.pause()
    }
    
    override fun onCleared() {
        super.onCleared()
        player.release()
        playerPositionTracker.release()
    }
}
private const val MIN_UPDATE_INTERVAL_MS: Long = 200
private const val MAX_UPDATE_INTERVAL_MS: Long = 1000

class PlayerPositionTracker {

    val handlerUpdatePosition =                                                                                                                                                                                                                                                                                                                                                                                                                                                  Handler(Looper.getMainLooper())
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

fun interface PlayerPositionListener {
    fun onPlayerPositionChanged(position: Long)
}