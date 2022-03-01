package com.supdeweb.androidmusicproject.data.remote.api

import com.google.gson.annotations.SerializedName
import com.supdeweb.androidmusicproject.data.remote.dto.TrackDto
import retrofit2.http.GET

interface TrackApi {

    /**
     * Album
     */
    @GET(TRACKS)
    suspend fun getTracks(): TrackResponse


    companion object {
        //TRACK
        private const val TRACKS = "mostloved.php?format=track"
    }
}

data class TrackResponse(
    @SerializedName("loved")
    val tracks: List<TrackDto>? = null,
)
