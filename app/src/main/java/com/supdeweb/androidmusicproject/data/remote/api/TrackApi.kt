package com.supdeweb.androidmusicproject.data.remote.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.supdeweb.androidmusicproject.data.remote.dto.track.TrackDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TrackApi {

    /**
     * get tracks
     */
    @GET(TRACKS)
    suspend fun getTracks(): TrackResponse

    /**
     * get trending tracks
     */
    @GET(TRENDING_TRACKS)
    fun getTrendingTracks(): Call<TrendingResponse>

    /**
     *
     */
    @GET(TRACKS_ALBUM)
    fun getTracksByAlbum(
        @Query("m") albumId: String,
    ): Call<TrackByAlbumResponse>


    companion object {
        //TRACK
        private const val TRACKS = "mostloved.php?format=track"
        private const val TRACKS_ALBUM = "track.php"
        private const val TRENDING_TRACKS = "trending.php?country=us&type=itunes&format=singles"
    }
}

data class TrackResponse(
    @SerializedName("loved")
    @Expose
    val tracks: List<TrackDto>? = null,
)


data class TrackByAlbumResponse(
    @SerializedName("track")
    @Expose
    val tracks: List<TrackDto>? = null,
)
