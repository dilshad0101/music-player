package com.app.musicplayer.data.track.remote

import com.app.musicplayer.data.track.TrackDataSource
import com.app.musicplayer.data.track.Track
import javax.inject.Inject

class TrackRemoteTrackDataSource @Inject constructor() : TrackDataSource {
    override suspend fun getTracks(): List<Track> {
        return listOf(
            Track(
                id = "1",
                title = "500lbs",
                artist = "Lil Tecca",
                album = "500lbs",
                coverArtUrl = "https://drive.google.com/u/1/uc?id=1G47jgsMSn5N2N4lXgrXl0pEIQjb-ox6X&export=download",
                trackUrl = "https://drive.google.com/u/1/uc?id=1XANDXBWCADuMff2EnvXtnEv4y13m9Ue1&export=download",
                lyricUrl = "URL_OF_LYRICS_1",
                albumId = "1"
            ),
            Track(
                id = "2",
                title = "War Bout It",
                artist = "Lil Durk",
                album = "Almost Healed",
                coverArtUrl = "https://drive.google.com/u/1/uc?id=1toWVHHz1pCFBD2OtMb90YNk7x7nkQ55Y&export=download",
                trackUrl = "https://drive.google.com/u/1/uc?id=1OyrhXV6KUAlCslclpL1Bssu-rnCtcqJB&export=download",
                lyricUrl = "URL_OF_LYRICS_2",
                albumId = "1"
            ),
            Track(
                id = "3",
                title = "Track 3",
                artist = "Artist 3",
                album = "Album B",
                coverArtUrl = "https://www.theglobeandmail.com/resizer/qqWRUfhhRCGq_uGzVZgCdcwR_0U=/1200x0/filters:quality(80):format(jpeg)/arc-anglerfish-tgam-prod-tgam.s3.amazonaws.com/public/3YV2PTJAVFGCVJK5IC6RJYY6EA",
                trackUrl = "URL_OF_TRACK_3",
                lyricUrl = "URL_OF_LYRICS_3",
                albumId = "2"
            ),
            Track(
                id = "4",
                title = "Track 4",
                artist = "Artist 4",
                album = "Album C",
                coverArtUrl = "https://www.theglobeandmail.com/resizer/qqWRUfhhRCGq_uGzVZgCdcwR_0U=/1200x0/filters:quality(80):format(jpeg)/arc-anglerfish-tgam-prod-tgam.s3.amazonaws.com/public/3YV2PTJAVFGCVJK5IC6RJYY6EA",
                trackUrl = "URL_OF_TRACK_4",
                lyricUrl = "URL_OF_LYRICS_4",
                albumId = "3"
            ),
            Track(
                id = "5",
                title = "Track 5",
                artist = "Artist 5",
                album = "Album A",
                coverArtUrl = "https://www.theglobeandmail.com/resizer/qqWRUfhhRCGq_uGzVZgCdcwR_0U=/1200x0/filters:quality(80):format(jpeg)/arc-anglerfish-tgam-prod-tgam.s3.amazonaws.com/public/3YV2PTJAVFGCVJK5IC6RJYY6EA",
                trackUrl = "URL_OF_TRACK_5",
                lyricUrl = "URL_OF_LYRICS_5",
                albumId = "1"
            ),
            Track(
                id = "6",
                title = "Track 6",
                artist = "Artist 6",
                album = "Album B",
                coverArtUrl = "https://www.theglobeandmail.com/resizer/qqWRUfhhRCGq_uGzVZgCdcwR_0U=/1200x0/filters:quality(80):format(jpeg)/arc-anglerfish-tgam-prod-tgam.s3.amazonaws.com/public/3YV2PTJAVFGCVJK5IC6RJYY6EA",
                trackUrl = "URL_OF_TRACK_6",
                lyricUrl = "URL_OF_LYRICS_6",
                albumId = "2"
            ),
            Track(
                id = "7",
                title = "Track 7",
                artist = "Artist 7",
                album = "Album C",
                coverArtUrl = "https://www.theglobeandmail.com/resizer/qqWRUfhhRCGq_uGzVZgCdcwR_0U=/1200x0/filters:quality(80):format(jpeg)/arc-anglerfish-tgam-prod-tgam.s3.amazonaws.com/public/3YV2PTJAVFGCVJK5IC6RJYY6EA",
                trackUrl = "URL_OF_TRACK_7",
                lyricUrl = "URL_OF_LYRICS_7",
                albumId = "4"
            )
        )

    }

    override suspend fun getTrack(id: Int): Track {
        TODO("Not yet implemented")
    }

}