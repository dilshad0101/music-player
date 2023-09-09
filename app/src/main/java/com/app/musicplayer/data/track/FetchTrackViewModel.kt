package com.app.musicplayer.data.track

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FetchTrackViewModel @Inject constructor(
    private val trackRepository: TrackRepository
) : ViewModel() {
    val trackList: MutableState<List<Album>> =  mutableStateOf(listOf())

    val personalizedAlbum = mutableStateListOf<Album>()

    init {
        getTracks()
    }
    fun generatePersonalizedPlaylist(keys: List<String?>){
        Log.d("YAH0 keys",keys.toString())
        var tracks: List<Track> = emptyList()

        keys.forEach{
            viewModelScope.launch {
                val result = it?.let { it1 -> trackRepository.requestTracks(it1) }
                if (result != null) {
                    tracks = result
                }

                if(tracks.isNotEmpty()){
                    val createdAlbum = tracks[0].coverArtUrl?.let {
                        Album(
                            id = "generatedInRuntime+${tracks.hashCode()}",
                            title = tracks[0].artist[0].name + " Mix",
                            artist = "",
                            tracks = tracks,
                            coverArtUrl = it
                        )
                    }
                    if (createdAlbum!= null && !personalizedAlbum.contains(createdAlbum)){
                        personalizedAlbum.add(createdAlbum)
                    }
                }
            }
        }

    }
    private fun getTracks(){
        viewModelScope.launch {
            trackList.value = trackRepository.getTracks()
        }
    }

    fun getAlbum(find:String?=null): List<Album> {
        val result = trackList.value
        return if (find == null){
           result
        }else {
            return result.filter { it.id == find }
        }
    }


}