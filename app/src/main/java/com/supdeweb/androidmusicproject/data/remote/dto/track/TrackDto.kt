package com.supdeweb.androidmusicproject.data.remote.dto.track

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TrackDto(
    @SerializedName("idTrack")
    @Expose
    val idTrack: String,

    @SerializedName("idAlbum")
    @Expose
    val idAlbum: String?,

    @SerializedName("idArtist")
    @Expose
    val idArtist: String?,

    @SerializedName("idLyric")
    @Expose
    val idLyric: String?,

    @SerializedName("idIMVDB")
    @Expose
    val idIMVDB: String?,

    @SerializedName("strTrack")
    @Expose
    val strTrack: String?,

    @SerializedName("strAlbum")
    @Expose
    val strAlbum: String?,

    @SerializedName("strArtist")
    @Expose
    val strArtist: String?,

    @SerializedName("strArtistAlternate")
    @Expose
    val strArtistAlternate: String?,

    @SerializedName("intCD")
    @Expose
    val intCD: String?,

    @SerializedName("intDuration")
    @Expose
    val intDuration: String?,

    @SerializedName("strGenre")
    @Expose
    val strGenre: String?,

    @SerializedName("strMood")
    @Expose
    val strMood: String?,

    @SerializedName("strStyle")
    @Expose
    val strStyle: String?,

    @SerializedName("strTheme")
    @Expose
    val strTheme: String?,

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

    @SerializedName("strTrackThumb")
    @Expose
    val strTrackThumb: String?,

    @SerializedName("strTrack3DCase")
    @Expose
    val strTrack3DCase: String?,

    @SerializedName("strTrackLyrics")
    @Expose
    val strTrackLyrics: String?,

    @SerializedName("strMusicVid")
    @Expose
    val strMusicVid: String?,

    @SerializedName("strMusicVidDirector")
    @Expose
    val strMusicVidDirector: String?,

    @SerializedName("strMusicVidCompany")
    @Expose
    val strMusicVidCompany: String?,

    @SerializedName("strMusicVidScreen1")
    @Expose
    val strMusicVidScreen1: String?,

    @SerializedName("strMusicVidScreen2")
    @Expose
    val strMusicVidScreen2: String?,

    @SerializedName("strMusicVidScreen3")
    @Expose
    val strMusicVidScreen3: String?,

    @SerializedName("intMusicVidViews")
    @Expose
    val intMusicVidViews: String?,

    @SerializedName("intMusicVidLikes")
    @Expose
    val intMusicVidLikes: String?,

    @SerializedName("intMusicVidDislikes")
    @Expose
    val intMusicVidDislikes: String?,

    @SerializedName("intMusicVidFavorites")
    @Expose
    val intMusicVidFavorites: String?,

    @SerializedName("intMusicVidComments")
    @Expose
    val intMusicVidComments: String?,

    @SerializedName("intTrackNumber")
    @Expose
    val intTrackNumber: String?,

    @SerializedName("intLoved")
    @Expose
    val intLoved: String?,

    @SerializedName("intScore")
    @Expose
    val intScore: Double,
    @SerializedName("intScoreVotes")
    @Expose
    val intScoreVotes: String?,

    @SerializedName("intTotalListeners")
    @Expose
    val intTotalListeners: String?,

    @SerializedName("intTotalPlays")
    @Expose
    val intTotalPlays: String?,

    @SerializedName("strMusicBrainzID")
    @Expose
    val strMusicBrainzID: String?,

    @SerializedName("strMusicBrainzAlbumID")
    @Expose
    val strMusicBrainzAlbumID: String?,

    @SerializedName("strMusicBrainzArtistID")
    @Expose
    val strMusicBrainzArtistID: String?,

    @SerializedName("strLocked")
    @Expose
    val strLocked: String?,
)