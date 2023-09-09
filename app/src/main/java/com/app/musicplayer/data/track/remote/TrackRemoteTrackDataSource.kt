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
            CoroutineScope(Dispatchers.IO).launch {
                val task = Firebase.firestore.collection(Constants.ALBUMS).get().await()
                task.forEach { album ->
                    val albumTitle = album.getString(Constants.TITLE)
                    val albumArtist = album.getString(Constants.ARTIST)
                    val albumId = album.getString(Constants.ID)
                    val albumCoverArtUrlPath:String? = album.getString(Constants.COVER_ART)
                    val trackList:MutableList<Track> = mutableListOf()
                    if(album.exists()){
                        val fetchedTrackList = album.reference.collection(Constants.TRACKS).get().await()
                        fetchedTrackList.forEach {track->
                            val artistsMap = track.get(Constants.ARTIST)
                            val listOfArtist = mutableListOf<Artist>()
                            if (artistsMap is List<*>) {
                                artistsMap.forEach { artistItem ->
                                    if (artistItem is Map<*, *>) {
                                        val name = artistItem["artist"] as? String
                                        val id = artistItem["id"] as? String

                                        if (!name.isNullOrBlank() && !id.isNullOrBlank()) {
                                            val artist = Artist(id = id, name = name)
                                            listOfArtist.add(artist)
                                        }
                                    }
                                }
                            }
                            val trackTitle = track.getString(Constants.TITLE)
                            val trackUri = track.getString(Constants.TRACK_URI)
                            Log.d("5147th", "${trackUri.isNullOrBlank()}" +"${trackTitle.isNullOrBlank()} ${albumTitle.isNullOrBlank()} ${albumCoverArtUrlPath.isNullOrBlank()} " )
                            if (!trackTitle.isNullOrBlank() && !trackUri.isNullOrBlank() && !albumTitle.isNullOrBlank() && !albumCoverArtUrlPath.isNullOrBlank()){
                                CoroutineScope(Dispatchers.IO).launch{
                                    val trackUrl = storage.child(trackUri).downloadUrl.await()
                                    val coverArtUrl = storage.child(albumCoverArtUrlPath).downloadUrl.await()
                                    trackList.add(
                                        Track(
                                            id = trackUri.toString(),
                                            title = trackTitle,
                                            album = albumTitle,
                                            artist = listOfArtist,
                                            trackUrl = trackUrl.toString(),
                                            lyricUrl = null,
                                            albumId = albumTitle.lowercase(),
                                            coverArtUrl = coverArtUrl.toString()
                                        )
                                    )
                                }
                            }
                        }

                        if (!albumId.isNullOrBlank() && !albumTitle.isNullOrBlank() && !albumCoverArtUrlPath.isNullOrBlank() && !albumArtist.isNullOrBlank() ) {
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

                        }
                    }
                }
                if (albumsList.size == task.size()) {
                    continuation.resume(albumsList)
                    this.cancel()
                }

            }

        }
        catch (e: Exception){
            Log.d("TAG:514 Exception while Fetching Tracks From Remote Database", e.printStackTrace().toString())
            e.printStackTrace()
            continuation.resume(albumsList)

        }
    }

    override suspend fun requestTracks(key: String) = suspendCoroutine<MutableList<Track>> { continuation->
        val db = Firebase.firestore
        val albumsCollection = db.collection(Constants.ALBUMS)
        val matchingTracks = mutableListOf<Track>()
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val querySnapshot = albumsCollection.get().await()
                for (albumDocument in querySnapshot) {
                    val albumId =  albumDocument.getString(Constants.ID)
                    val albumTitle =  albumDocument.getString(Constants.TITLE)

                    val tracksCollection = albumDocument.reference.collection(Constants.TRACKS)
                    val genreQuery = tracksCollection.whereArrayContains(Constants.ATTRIBUTE, key)
                    val nestedQuerySnapshot = genreQuery.get().await()
                    for (nestedDocument in nestedQuerySnapshot.documents) {
                        val artistsMap = nestedDocument.get(Constants.ARTIST)
                        val listOfArtist = mutableListOf<Artist>()
                        if (artistsMap is List<*>) {
                            artistsMap.forEach { artistItem ->
                                if (artistItem is Map<*, *>) {
                                    val name = artistItem["artist"] as? String
                                    val id = artistItem["id"] as? String

                                    if (!name.isNullOrBlank() && !id.isNullOrBlank()) {
                                        val artist = Artist( id = id,name = name)
                                        listOfArtist.add(artist)
                                    }
                                }
                            }
                        }
                        val trackTitle = nestedDocument.getString(Constants.TITLE)
                        val trackUri = nestedDocument.getString(Constants.TRACK_URI)
                        val url = trackUri?.let { storage.child(it).downloadUrl.await() }
                        val albumCover= albumDocument.getString(Constants.COVER_ART)
                            ?.let { storage.child(it).downloadUrl.await() }

                        val track : Track? =
                            if(albumId != null && trackTitle != null && albumTitle!= null && url != null){
                                Track(
                                    id = trackUri.toString(),
                                    title = trackTitle,
                                    artist = listOfArtist,
                                    album = albumTitle,
                                    lyricUrl = null,
                                    trackUrl = url.toString(),
                                    albumId = albumId,
                                    coverArtUrl = albumCover.toString()
                                )
                            } else {
                                null
                            }
                        if (track != null) matchingTracks.add(track)
                    }
                }
                if (matchingTracks.size == querySnapshot!!.size()) {
                    continuation.resume(matchingTracks)
                    this.cancel()
                }

            }

        }catch (e:Exception){
            e.printStackTrace()

        }
    }
    override suspend fun getTrack(id: Int): Track {
        TODO("Not yet implemented")
    }

}
