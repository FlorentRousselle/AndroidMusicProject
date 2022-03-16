package com.supdeweb.androidmusicproject.data.remote.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.supdeweb.androidmusicproject.data.remote.dto.album.AlbumDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

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

    /**
     *
     */
    @GET(ALBUM_DETAIL)
    fun getAlbumDetail(
        @Query("m") albumId: String,
    ): Call<GetAlbumDetailResponse>

    /**
     *
     */
    @GET(ALBUM_DETAIL)
    fun getAlbumsByArtist(
        @Query("i") artistId: String,
    ): Call<GetAlbumDetailResponse>


    companion object {
        //ALBUM
        private const val ALBUMS = "mostloved.php?format=album"
        private const val ALBUM_DETAIL = "album.php"
        private const val TRENDING_ALBUMS = "trending.php?country=us&type=itunes&format=albums"
    }
}

data class AlbumResponse(
    @SerializedName("loved")
    @Expose
    val albums: List<AlbumDto>? = null,
)

data class GetAlbumDetailResponse(
    @SerializedName("album")
    @Expose
    val album: List<AlbumDto>? = null,
)
