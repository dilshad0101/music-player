package com.app.musicplayer.data.track

import androidx.compose.runtime.MutableState
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
    val trackList: MutableState<List<Track>> =  mutableStateOf(listOf())

    init {
        getTracks()
    }

    fun getTracks(){
        viewModelScope.launch {
            trackList.value = trackRepository.getTracks()
        }
    }

    fun findTrack(id: String): Track?{
        return trackList.value.find { it.id == id }
    }
    fun getAlbum(find:String?=null): List<Album> {
        val tracks: List<Track> = trackList.value
        // Group tracks by album title
        val tracksByAlbum = tracks.groupBy { it.album }

        // Find the two most common artists
        val artistCountMap = tracks.groupingBy { it.artist }
            .eachCount()
            .toList()
            .sortedByDescending { it.second }
        val mostCommonArtists = artistCountMap.take(2).map { it.first }

        // Find the most common cover art URL
        val coverArtUrlCountMap = tracks.groupingBy { it.coverArtUrl }
            .eachCount()
            .toList()
            .maxByOrNull { it.second }
            ?.first

        val result = tracksByAlbum.map { (albumTitle, albumTracks) ->
            Album(
                title = albumTitle,
                artist = mostCommonArtists.joinToString(", "),
                tracks = albumTracks,
                coverArtUrl = coverArtUrlCountMap ?: "",
                id = albumTracks[0].albumId
            )
        }
        return if (find == null){
           result
        }else {
            return result.filter { it.id == find }
        }
    }


}