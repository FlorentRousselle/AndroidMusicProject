package com.supdeweb.androidmusicproject.data.remote.api

import com.google.gson.annotations.SerializedName
import com.supdeweb.androidmusicproject.data.remote.dto.album.AlbumDto
import retrofit2.Call
import retrofit2.http.GET

interface AlbumApi {

    /**
     * Albums
     */
    @GET(ALBUMS)
    suspend fun getAlbums(): AlbumResponse

    /**
     * Trending albumDtos
     */
    @GET(TRENDING_ALBUMS)
    fun getTrendingAlbums(): Call<TrendingResponse>


    companion object {
        //ALBUM
        private const val ALBUMS = "mostloved.php?format=album"
        private const val TRENDING_ALBUMS = "trending.php?country=us&type=itunes&format=albums"
    }
}

data class AlbumResponse(
    @SerializedName("loved")
    val albums: List<AlbumDto>? = null,
)
