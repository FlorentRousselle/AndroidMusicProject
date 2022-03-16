package com.supdeweb.androidmusicproject.data.remote.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.supdeweb.androidmusicproject.data.remote.dto.artist.ArtistDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistApi {

    /**
     *
     */
    @GET(ARTIST_DETAIL)
    fun getArtistDetail(
        @Query("i") artistId: String,
    ): Call<ArtistDetailResponse>

    companion object {
        //ALBUM
        private const val ARTIST_DETAIL = "artist.php"
    }
}

data class ArtistDetailResponse(
    @SerializedName("artists")
    @Expose
    val artists: List<ArtistDto>? = null,
)