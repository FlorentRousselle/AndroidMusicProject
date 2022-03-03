package com.supdeweb.androidmusicproject.data.remote.dto.album

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TrendingAlbumDto(
    @SerializedName("idTrend")
    @Expose
    val idTrend: String?,
    @SerializedName("intChartPlace")
    @Expose
    val intChartPlace: String?,
    @SerializedName("idArtist")
    @Expose
    val idArtist: String?,
    @SerializedName("idAlbum")
    @Expose
    val idAlbum: String,
    @SerializedName("idTrack")
    @Expose
    val idTrack: String?,
    @SerializedName("strArtistMBID")
    @Expose
    val strArtistMBID: String?,
    @SerializedName("strAlbumMBID")
    @Expose
    val strAlbumMBID: String?,
    @SerializedName("strTrackMBID")
    @Expose
    val strTrackMBID: String?,
    @SerializedName("strArtist")
    @Expose
    val strArtist: String?,
    @SerializedName("strAlbum")
    @Expose
    val strAlbum: String?,
    @SerializedName("strTrack")
    @Expose
    val strTrack: String?,
    @SerializedName("strArtistThumb")
    @Expose
    val strArtistThumb: String?,
    @SerializedName("strAlbumThumb")
    @Expose
    val strAlbumThumb: String?,
    @SerializedName("strTrackThumb")
    @Expose
    val strTrackThumb: String?,
    @SerializedName("strCountry")
    @Expose
    val strCountry: String?,
    @SerializedName("strType")
    @Expose
    val strType: String?,
    @SerializedName("intWeek")
    @Expose
    val intWeek: String?,
    @SerializedName("dateAdded")
    @Expose
    val dateAdded: String?,
)