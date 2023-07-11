package com.app.musicplayer.data.featuredContent

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.musicplayer.data.track.Track
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FeaturedContentViewModel @Inject constructor(
    private val repository: FeaturedContentRepository
): ViewModel() {
    val featureContentList: MutableState<List<FeaturedContent>> =  mutableStateOf(listOf())

    init {
        viewModelScope.launch {
            featureContentList.value = repository.getContent()
        }

    }
}