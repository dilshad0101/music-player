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
    val trackList: MutableState<List<Album>> =  mutableStateOf(listOf())

    init {
        getTracks()
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