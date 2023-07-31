package com.app.musicplayer.player

import android.app.Application
import android.app.Service
import android.media.MediaMetadata
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Tracks
import androidx.media3.session.MediaLibraryService
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.app.musicplayer.data.track.Album
import com.app.musicplayer.data.track.Track
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class MediaPlayerViewModel @Inject constructor(
    val player : Player
) : ViewModel() {
    var playbackPosition = mutableStateOf<Long>(0)
    var totalDuration = mutableStateOf<Long>(0)
    var currentTrack : MutableState<Track?> = mutableStateOf(null)
    var currentQueue: MutableState<Album?> = mutableStateOf(null)
    private var currentMediaItems = emptyList<MediaItem>()
    private val playerPositionTracker= PlayerPositionTracker()

    private val listener = object : Player.Listener{
        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            super.onMediaItemTransition(mediaItem, reason)
            totalDuration.value = player.duration.coerceAtLeast(0L)
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
        player.addListener(listener)
        player.prepare()
    }

    fun updatePlaybackPosition(){
        viewModelScope.launch {
            while (true){
                updatePosition()
                Log.d("TAG222",playbackPosition.value.toString()+ player.playbackSuppressionReason)
                playbackPosition.value = player.currentPosition
                totalDuration.value = player.duration.coerceAtLeast(0L)
                delay(1.seconds/8)
            }
        }
    }

    fun setQueue(album: Album){
        var mediaItemList = mutableListOf<MediaItem>()
        for (track in album.tracks){
            val uri = track.trackUrl
            val mediaItem: MediaItem = MediaItem.Builder()
                .setMediaId(track.id)
                .setUri(uri)
                .build()

            mediaItemList.add(mediaItem)
        }
        currentMediaItems = mediaItemList
        player.setMediaItems(mediaItemList)
    }
    fun moveToNext(currentTrackId: String){
        if(player.hasNextMediaItem()){
            val indexOfCurrentTrack = currentQueue.value?.tracks?.indexOfFirst { it.id == currentTrackId }
            if (indexOfCurrentTrack != null) {
                currentTrack.value = currentQueue.value?.tracks?.get(indexOfCurrentTrack + 1)
                player.seekToNextMediaItem()
            }
        }
    }

    fun moveToPrevious(currentTrackId: String){
        if(player.hasPreviousMediaItem()){
            val indexOfCurrentTrack = currentQueue.value?.tracks?.indexOfFirst { it.id == currentTrackId }
            if (indexOfCurrentTrack != null){
                currentTrack.value = currentQueue.value?.tracks?.get(indexOfCurrentTrack - 1)
                player.seekToPreviousMediaItem()
            }
        }
    }
    fun onSeek(newPosition:Float){
        Log.wtf("TAG12", "Seek: $newPosition Total: ${player.duration.toFloat()}" )
        player.seekTo(newPosition.toLong())
    }
    private fun updatePosition(player: Player = this.player) {
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
    fun setTrack(uri: String){
        val selectedTrack = currentMediaItems.indexOfFirst { it.mediaId == uri.toString() }
        Log.d("TAG459","FIRST $selectedTrack.toString() $currentMediaItems" )
        if(selectedTrack != -1){
            Log.d("TAG459","$selectedTrack.toString() $currentMediaItems" )
            player.seekToDefaultPosition(selectedTrack)
        }
    }
    fun play(){
        Log.wtf("TAG1122",currentQueue.value.toString() + currentTrack.value.toString())
        if (player.playbackState == Player.STATE_IDLE){
            player.prepare()
        }
        player.play()

    }

    fun pause(){
        player.pause()
    }

    override fun onCleared() {
        super.onCleared()
        player.removeListener(listener)
        player.release()
        playerPositionTracker.release()
    }
}


