package com.supdeweb.androidmusicproject.data.remote.dto.album

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AlbumDto(
    @SerializedName("idAlbum")
    @Expose
    val idAlbum: String,
    @SerializedName("idArtist")
    @Expose
    val idArtist: String?,
    @SerializedName("idLabel")
    @Expose
    val idLabel: String?,
    @SerializedName("strAlbum")
    @Expose
    val strAlbum: String?,
    @SerializedName("strAlbumStripped")
    @Expose
    val strAlbumStripped: String?,
    @SerializedName("strArtist")
    @Expose
    val strArtist: String?,
    @SerializedName("strArtistStripped")
    @Expose
    val strArtistStripped: String?,
    @SerializedName("intYearReleased")
    @Expose
    val intYearReleased: String?,
    @SerializedName("strStyle")
    @Expose
    val strStyle: String?,
    @SerializedName("strGenre")
    @Expose
    val strGenre: String?,
    @SerializedName("strLabel")
    @Expose
    val strLabel: String?,
    @SerializedName("strReleaseFormat")
    @Expose
    val strReleaseFormat: String?,
    @SerializedName("intSales")
    @Expose
    val intSales: String?,
    @SerializedName("strAlbumThumb")
    @Expose
    val strAlbumThumb: String?,
    @SerializedName("strAlbumThumbHQ")
    @Expose
    val strAlbumThumbHQ: String?,
    @SerializedName("strAlbumThumbBack")
    @Expose
    val strAlbumThumbBack: String?,
    @SerializedName("strAlbumCDart")
    @Expose
    val strAlbumCDart: String?,
    @SerializedName("strAlbumSpine")
    @Expose
    val strAlbumSpine: String?,
    @SerializedName("strAlbum3DCase")
    @Expose
    val strAlbum3DCase: String?,
    @SerializedName("strAlbum3DFlat")
    @Expose
    val strAlbum3DFlat: String?,
    @SerializedName("strAlbum3DFace")
    @Expose
    val strAlbum3DFace: String?,
    @SerializedName("strAlbum3DThumb")
    @Expose
    val strAlbum3DThumb: String?,
    @SerializedName("strDescription")
    @Expose
    val strDescription: String?,
    @SerializedName("strDescriptionDE")
    @Expose
    val strDescriptionDE: String?,
    @SerializedName("strDescriptionFR")
    @Expose
    val strDescriptionFR: String?,
    @SerializedName("strDescriptionCN")
    @Expose
    val strDescriptionCN: String?,
    @SerializedName("strDescriptionIT")
    @Expose
    val strDescriptionIT: String?,
    @SerializedName("strDescriptionJP")
    @Expose
    val strDescriptionJP: String?,
    @SerializedName("strDescriptionRU")
    @Expose
    val strDescriptionRU: String?,
    @SerializedName("strDescriptionES")
    @Expose
    val strDescriptionES: String?,
    @SerializedName("strDescriptionPT")
    @Expose
    val strDescriptionPT: String?,
    @SerializedName("strDescriptionSE")
    @Expose
    val strDescriptionSE: String?,
    @SerializedName("strDescriptionNL")
    @Expose
    val strDescriptionNL: String?,
    @SerializedName("strDescriptionHU")
    @Expose
    val strDescriptionHU: String?,
    @SerializedName("strDescriptionNO")
    @Expose
    val strDescriptionNO: String?,
    @SerializedName("strDescriptionIL")
    @Expose
    val strDescriptionIL: String?,
    @SerializedName("strDescriptionPL")
    @Expose
    val strDescriptionPL: String?,
    @SerializedName("intLoved")
    @Expose
    val intLoved: String?,
    @SerializedName("intScore")
    @Expose
    val intScore: String?,
    @SerializedName("intScoreVotes")
    @Expose
    val intScoreVotes: String?,
    @SerializedName("strReview")
    @Expose
    val strReview: String?,
    @SerializedName("strMood")
    @Expose
    val strMood: String?,
    @SerializedName("strTheme")
    @Expose
    val strTheme: String?,
    @SerializedName("strSpeed")
    @Expose
    val strSpeed: String?,
    @SerializedName("strLocation")
    @Expose
    val strLocation: String?,
    @SerializedName("strMusicBrainzID")
    @Expose
    val strMusicBrainzID: String?,
    @SerializedName("strMusicBrainzArtistID")
    @Expose
    val strMusicBrainzArtistID: String?,
    @SerializedName("strAllMusicID")
    @Expose
    val strAllMusicID: String?,
    @SerializedName("strBBCReviewID")
    @Expose
    val strBBCReviewID: String?,
    @SerializedName("strRateYourMusicID")
    @Expose
    val strRateYourMusicID: String?,
    @SerializedName("strDiscogsID")
    @Expose
    val strDiscogsID: String?,
    @SerializedName("strWikidataID")
    @Expose
    val strWikidataID: String?,
    @SerializedName("strWikipediaID")
    @Expose
    val strWikipediaID: String?,
    @SerializedName("strGeniusID")
    @Expose
    val strGeniusID: String?,
    @SerializedName("strLyricWikiID")
    @Expose
    val strLyricWikiID: String?,
    @SerializedName("strMusicMozID")
    @Expose
    val strMusicMozID: String?,
    @SerializedName("strItunesID")
    @Expose
    val strItunesID: String?,
    @SerializedName("strAmazonID")
    @Expose
    val strAmazonID: String?,
    @SerializedName("strLocked")
    @Expose
    val strLocked: String?,
)