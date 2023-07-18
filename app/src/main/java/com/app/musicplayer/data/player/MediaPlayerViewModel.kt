package com.app.musicplayer.data.player

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaPlayerViewModel @Inject constructor(
    val player : Player
) : ViewModel() {

    var playbackPosition by mutableStateOf<Long>(0)

    init {
        player.prepare()
        viewModelScope.launch {
            playbackPosition = player.currentPosition
        }
    }
    fun onSeek(newPosition:Long){
        player.seekTo(newPosition)
    }
    fun maxDuration(): Long{
        return if (player.contentDuration != C.TIME_UNSET){
            player.contentDuration
        }else{
            1
        }
    }
    fun isPlaying():Boolean{
        return player.isPlaying
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

    fun getTime(){
        player.videoSize
    }
    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}