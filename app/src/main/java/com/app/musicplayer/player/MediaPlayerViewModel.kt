package com.app.musicplayer.player

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.*
import com.app.musicplayer.data.track.Album
import com.app.musicplayer.data.track.Track
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds


class MediaPlayerViewModel(): ViewModel() {
    var player: MediaController? = null
    var playbackPosition = mutableStateOf<Long>(0)
    var totalDuration = mutableStateOf<Long>(0)
    var currentTrack : MutableState<Track?> = mutableStateOf(null)
    var currentQueue: MutableState<Album?> = mutableStateOf(null)
    private var currentMediaItems = emptyList<MediaItem>()
    private val playerPositionTracker= PlayerPositionTracker()

    private val listener = object : Player.Listener{
        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            super.onMediaItemTransition(mediaItem, reason)
            totalDuration.value = player?.duration?.coerceAtLeast(0L) ?: 0L
            if(mediaItem != null && reason == Player.MEDIA_ITEM_TRANSITION_REASON_AUTO ){
                val transitionedMediaItem = currentQueue.value?.tracks?.find { it.id == mediaItem.mediaId }
                if (transitionedMediaItem != null){
                    currentTrack.value = transitionedMediaItem
                }
            }

        }

        override fun onEvents(player: Player, events: Player.Events) {
            super.onEvents(player, events)
            totalDuration.value = player.duration.coerceAtLeast(0L)
        }
    }

    init {
        player?.addListener(listener)
        player?.prepare()
    }

    fun installController(controller: MediaController?){
        player = controller
    }
    fun updatePlaybackPosition(){
        viewModelScope.launch {
            while (true){
                updatePosition()
                playbackPosition.value = player?.currentPosition ?: 0L
                totalDuration.value = player?.duration?.coerceAtLeast(0L) ?: 0L
                delay(1.seconds/8)
            }
        }
    }

    fun setQueue(album: Album){
        val mediaItemList = mutableListOf<MediaItem>()
        for (track in album.tracks){
            val uri = track.trackUrl
            val mediaItem: MediaItem = MediaItem.Builder()
                .setMediaId(track.id)
                .setUri(uri)
                .build()

            mediaItemList.add(mediaItem)
        }
        currentMediaItems = mediaItemList
        player?.setMediaItems(mediaItemList)
    }
    fun moveToNext(currentTrackId: String){
        if(player?.hasNextMediaItem() == true){
            val indexOfCurrentTrack = currentQueue.value?.tracks?.indexOfFirst { it.id == currentTrackId }
            if (indexOfCurrentTrack != null) {
                currentTrack.value = currentQueue.value?.tracks?.get(indexOfCurrentTrack + 1)
                player?.seekToNextMediaItem()
            }
        }
    }

    fun moveToPrevious(currentTrackId: String){
        if(player?.hasPreviousMediaItem() == true){
            val indexOfCurrentTrack = currentQueue.value?.tracks?.indexOfFirst { it.id == currentTrackId }
            if (indexOfCurrentTrack != null){
                currentTrack.value = currentQueue.value?.tracks?.get(indexOfCurrentTrack - 1)
                player?.seekToPreviousMediaItem()
            }
        }
    }
    fun onSeek(newPosition:Float){
        player?.seekTo(newPosition.toLong())
    }
    private fun updatePosition(player: MediaController? = this.player) {
        val handlerUpdatePosition = playerPositionTracker.handlerUpdatePosition
        val runnableUpdatePosition = playerPositionTracker.runnableUpdatePosition
        val playerPositionListener = playerPositionTracker.playerPositionListener
        handlerUpdatePosition.removeCallbacks(runnableUpdatePosition)

        if (player!=null){
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
    }
    fun setTrack(uri: String){
        val selectedTrack = currentMediaItems.indexOfFirst { it.mediaId == uri.toString() }
        Log.d("TAG459","FIRST $selectedTrack.toString() $currentMediaItems" )
        if(selectedTrack != -1){
            Log.d("TAG459","$selectedTrack.toString() $currentMediaItems" )
            player?.seekToDefaultPosition(selectedTrack)
        }
    }
    fun play(){
        Log.d("ok223","TRYING To PLay ${player?.playbackState}")

        Log.wtf("TAG1122",currentQueue.value.toString() + currentTrack.value.toString())
        if ((player?.playbackState ?: 69) == Player.STATE_IDLE){
            player?.prepare()
        }
        player?.play()
    }

    fun pause(){
        player?.pause()
    }

    override fun onCleared() {
        super.onCleared()
        player?.removeListener(listener)
        player?.release()
        playerPositionTracker.release()
    }
}


