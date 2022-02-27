package com.supdeweb.androidmusicproject.data.remote.dto.album

import com.google.gson.annotations.SerializedName

data class AlbumDto(
    @SerializedName("idAlbum")
    val idAlbum: String,
    @SerializedName("idArtist")
    val idArtist: String?,
    @SerializedName("idLabel")
    val idLabel: String?,
    @SerializedName("strAlbum")
    val strAlbum: String?,
    @SerializedName("strAlbumStripped")
    val strAlbumStripped: String?,
    @SerializedName("strArtist")
    val strArtist: String?,
    @SerializedName("strArtistStripped")
    val strArtistStripped: String?,
    @SerializedName("intYearReleased")
    val intYearReleased: String?,
    @SerializedName("strStyle")
    val strStyle: String?,
    @SerializedName("strGenre")
    val strGenre: String?,
    @SerializedName("strLabel")
    val strLabel: String?,
    @SerializedName("strReleaseFormat")
    val strReleaseFormat: String?,
    @SerializedName("intSales")
    val intSales: String?,
    @SerializedName("strAlbumThumb")
    val strAlbumThumb: String?,
    @SerializedName("strAlbumThumbHQ")
    val strAlbumThumbHQ: String?,
    @SerializedName("strAlbumThumbBack")
    val strAlbumThumbBack: String?,
    @SerializedName("strAlbumCDart")
    val strAlbumCDart: String?,
    @SerializedName("strAlbumSpine")
    val strAlbumSpine: String?,
    @SerializedName("strAlbum3DCase")
    val strAlbum3DCase: String?,
    @SerializedName("strAlbum3DFlat")
    val strAlbum3DFlat: String?,
    @SerializedName("strAlbum3DFace")
    val strAlbum3DFace: String?,
    @SerializedName("strAlbum3DThumb")
    val strAlbum3DThumb: String?,
    @SerializedName("strDescription")
    val strDescription: String?,
    @SerializedName("strDescriptionDE")
    val strDescriptionDE: String?,
    @SerializedName("strDescriptionFR")
    val strDescriptionFR: String?,
    @SerializedName("strDescriptionCN")
    val strDescriptionCN: String?,
    @SerializedName("strDescriptionIT")
    val strDescriptionIT: String?,
    @SerializedName("strDescriptionJP")
    val strDescriptionJP: String?,
    @SerializedName("strDescriptionRU")
    val strDescriptionRU: String?,
    @SerializedName("strDescriptionES")
    val strDescriptionES: String?,
    @SerializedName("strDescriptionPT")
    val strDescriptionPT: String?,
    @SerializedName("strDescriptionSE")
    val strDescriptionSE: String?,
    @SerializedName("strDescriptionNL")
    val strDescriptionNL: String?,
    @SerializedName("strDescriptionHU")
    val strDescriptionHU: String?,
    @SerializedName("strDescriptionNO")
    val strDescriptionNO: String?,
    @SerializedName("strDescriptionIL")
    val strDescriptionIL: String?,
    @SerializedName("strDescriptionPL")
    val strDescriptionPL: String?,
    @SerializedName("intLoved")
    val intLoved: String?,
    @SerializedName("intScore")
    val intScore: String?,
    @SerializedName("intScoreVotes")
    val intScoreVotes: String?,
    @SerializedName("strReview")
    val strReview: String?,
    @SerializedName("strMood")
    val strMood: String?,
    @SerializedName("strTheme")
    val strTheme: String?,
    @SerializedName("strSpeed")
    val strSpeed: String?,
    @SerializedName("strLocation")
    val strLocation: String?,
    @SerializedName("strMusicBrainzID")
    val strMusicBrainzID: String?,
    @SerializedName("strMusicBrainzArtistID")
    val strMusicBrainzArtistID: String?,
    @SerializedName("strAllMusicID")
    val strAllMusicID: String?,
    @SerializedName("strBBCReviewID")
    val strBBCReviewID: String?,
    @SerializedName("strRateYourMusicID")
    val strRateYourMusicID: String?,
    @SerializedName("strDiscogsID")
    val strDiscogsID: String?,
    @SerializedName("strWikidataID")
    val strWikidataID: String?,
    @SerializedName("strWikipediaID")
    val strWikipediaID: String?,
    @SerializedName("strGeniusID")
    val strGeniusID: String?,
    @SerializedName("strLyricWikiID")
    val strLyricWikiID: String?,
    @SerializedName("strMusicMozID")
    val strMusicMozID: String?,
    @SerializedName("strItunesID")
    val strItunesID: String?,
    @SerializedName("strAmazonID")
    val strAmazonID: String?,
    @SerializedName("strLocked")
    val strLocked: String?,
)