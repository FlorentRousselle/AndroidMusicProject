package com.supdeweb.androidmusicproject.data.remote.api

import com.google.gson.annotations.SerializedName
import com.supdeweb.androidmusicproject.data.remote.dto.AlbumDto
import retrofit2.http.GET

interface AlbumApi {

    /**
     * Album
     */
    @GET(ALBUMS)
    suspend fun getAlbums(): AlbumResponse


    companion object {
        //ALBUM
        private const val ALBUMS = "mostloved.php?format=album"
    }
}

data class AlbumResponse(
    @SerializedName("loved")
    val albums: List<AlbumDto>? = null,
)
