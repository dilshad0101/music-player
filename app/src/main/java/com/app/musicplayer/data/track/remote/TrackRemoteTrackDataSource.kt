package com.app.musicplayer.data.track.remote

import android.util.Log
import com.app.musicplayer.data.track.Album
import com.app.musicplayer.data.track.Artist
import com.app.musicplayer.data.track.TrackDataSource
import com.app.musicplayer.data.track.Track
import com.app.musicplayer.presentation.utility.Constants
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class TrackRemoteTrackDataSource @Inject constructor() : TrackDataSource {
    private val storage = Firebase.storage.reference
    override suspend fun getTracks() = suspendCoroutine<List<Album>> { continuation->
        val albumsList:MutableList<Album> = mutableListOf()
        try{
            Firebase.firestore.collection(Constants.ALBUMS).get().addOnCompleteListener {task ->
                task.result.forEach { album ->
                    val albumTitle = album.getString(Constants.TITLE)
                    Log.d("5147th", albumTitle.toString())
                    val albumArtist = album.getString(Constants.ARTIST)
                    val albumId = album.getString(Constants.ID)
                    val albumCoverArtUrlPath:String? = album.getString(Constants.COVER_ART)
                    val trackList:MutableList<Track> = mutableListOf()
                    val result2 = mutableListOf<Job>()

                    if(album.exists()){
                        album.reference.collection(Constants.TRACKS).get()
                            .addOnSuccessListener {fetchedTrackList->
                                fetchedTrackList.forEach {track->
                                    val artistsMap = track.get(Constants.ARTIST)
                                    val listOfArtist = mutableListOf<Artist>()
                                    if (artistsMap is List<*>) {
                                        artistsMap.forEach { artistItem ->
                                            if (artistItem is Map<*, *>) {
                                                val name = artistItem["artist"] as? String
                                                val id = artistItem["id"] as? String

                                                if (!name.isNullOrBlank() && !id.isNullOrBlank()) {
                                                    val artist = Artist(name, id)
                                                    listOfArtist.add(artist)
                                                }
                                            }
                                        }
                                    }
                                    val trackTitle = track.getString(Constants.TITLE)
                                    val trackUri = track.getString(Constants.TRACK_URI)
                                    Log.d("5147th", "${trackUri.isNullOrBlank()}" +"${trackTitle.isNullOrBlank()} ${albumTitle.isNullOrBlank()} ${albumCoverArtUrlPath.isNullOrBlank()} " )
                                    if (!trackTitle.isNullOrBlank() && !trackUri.isNullOrBlank() && !albumTitle.isNullOrBlank() && !albumCoverArtUrlPath.isNullOrBlank()){
                                        Log.d("5147th saved TRACK", trackUri.toString())
                                        val result = CoroutineScope(Dispatchers.IO).launch{
                                            val url = storage.child(trackUri).downloadUrl.await()
                                            trackList.add(
                                                Track(
                                                    id = trackUri.toString(),
                                                    title = trackTitle,
                                                    album = albumTitle,
                                                    artist = listOfArtist,
                                                    trackUrl = url.toString(),
                                                    lyricUrl = null,
                                                    albumId = albumTitle.lowercase()
                                                )
                                            )
                                        }
                                        result2.add(result)


                                    }
                                }
                            }
                        Log.d("5147th", "${albumId.isNullOrBlank()}" +"${albumTitle.isNullOrBlank()} ${albumCoverArtUrlPath.isNullOrBlank()} ${albumArtist.isNullOrBlank()} " )

                        if (!albumId.isNullOrBlank() && !albumTitle.isNullOrBlank() && !albumCoverArtUrlPath.isNullOrBlank() && !albumArtist.isNullOrBlank() ) {
                            Log.d("5147th saved Album", albumTitle.toString())
                            runBlocking{
                                result2.joinAll()
                                val albumCoverArtUrl = storage.child(albumCoverArtUrlPath).downloadUrl.await()
                                albumsList.add(
                                    Album(
                                        id = albumId,
                                        title = albumTitle,
                                        artist = albumArtist,
                                        tracks = trackList,
                                        coverArtUrl = albumCoverArtUrl.toString(),
                                    )
                                )
                                Log.d("5147th22", albumCoverArtUrlPath.toString())

                            }

                        }
                    }
                }
                if (albumsList.size == task.result!!.size()) {
                    continuation.resume(albumsList)
                }
            }

        }
        catch (e: Exception){
            Log.d("5147 exception", e.printStackTrace().toString())
            e.printStackTrace()
            continuation.resume(albumsList)

        }
    }

    override suspend fun getTrack(id: Int): Track {
        TODO("Not yet implemented")
    }

}

