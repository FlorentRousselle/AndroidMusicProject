package com.supdeweb.androidmusicproject.data.remote.dto.artist

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ArtistDto(
    @SerializedName("idArtist")
    @Expose
    val idArtist: String,
    @SerializedName("strArtist")
    @Expose
    val strArtist: String?,
    @SerializedName("strArtistStripped")
    @Expose
    val strArtistStripped: String?,
    @SerializedName("strArtistAlternate")
    @Expose
    val strArtistAlternate: String?,
    @SerializedName("strLabel")
    @Expose
    val strLabel: String?,
    @SerializedName("idLabel")
    @Expose
    val idLabel: String?,
    @SerializedName("intFormedYear")
    @Expose
    val intFormedYear: String?,
    @SerializedName("intBornYear")
    @Expose
    val intBornYear: String?,
    @SerializedName("intDiedYear")
    @Expose
    val intDiedYear: String?,
    @SerializedName("strDisbanded")
    @Expose
    val strDisbanded: String?,
    @SerializedName("strStyle")
    @Expose
    val strStyle: String?,
    @SerializedName("strGenre")
    @Expose
    val strGenre: String?,
    @SerializedName("strMood")
    @Expose
    val strMood: String?,
    @SerializedName("strWebsite")
    @Expose
    val strWebsite: String?,
    @SerializedName("strFacebook")
    @Expose
    val strFacebook: String?,
    @SerializedName("strTwitter")
    @Expose
    val strTwitter: String?,
    @SerializedName("strBiographyEN")
    @Expose
    val strBiographyEN: String?,
    @SerializedName("strBiographyDE")
    @Expose
    val strBiographyDE: String?,
    @SerializedName("strBiographyFR")
    @Expose
    val strBiographyFR: String?,
    @SerializedName("strBiographyCN")
    @Expose
    val strBiographyCN: String?,
    @SerializedName("strBiographyIT")
    @Expose
    val strBiographyIT: String?,
    @SerializedName("strBiographyJP")
    @Expose
    val strBiographyJP: String?,
    @SerializedName("strBiographyRU")
    @Expose
    val strBiographyRU: String?,
    @SerializedName("strBiographyES")
    @Expose
    val strBiographyES: String?,
    @SerializedName("strBiographyPT")
    @Expose
    val strBiographyPT: String?,
    @SerializedName("strBiographySE")
    @Expose
    val strBiographySE: String?,
    @SerializedName("strBiographyNL")
    @Expose
    val strBiographyNL: String?,
    @SerializedName("strBiographyHU")
    @Expose
    val strBiographyHU: String?,
    @SerializedName("strBiographyNO")
    @Expose
    val strBiographyNO: String?,
    @SerializedName("strBiographyIL")
    @Expose
    val strBiographyIL: String?,
    @SerializedName("strBiographyPL")
    @Expose
    val strBiographyPL: String?,
    @SerializedName("strGender")
    @Expose
    val strGender: String?,
    @SerializedName("intMembers")
    @Expose
    val intMembers: String?,
    @SerializedName("strCountry")
    @Expose
    val strCountry: String?,
    @SerializedName("strCountryCode")
    @Expose
    val strCountryCode: String?,
    @SerializedName("strArtistThumb")
    @Expose
    val strArtistThumb: String?,
    @SerializedName("strArtistLogo")
    @Expose
    val strArtistLogo: String?,
    @SerializedName("strArtistCutout")
    @Expose
    val strArtistCutout: String?,
    @SerializedName("strArtistClearart")
    @Expose
    val strArtistClearart: String?,
    @SerializedName("strArtistWideThumb")
    @Expose
    val strArtistWideThumb: String?,
    @SerializedName("strArtistFanart")
    @Expose
    val strArtistFanart: String?,
    @SerializedName("strArtistFanart2")
    @Expose
    val strArtistFanart2: String?,
    @SerializedName("strArtistFanart3")
    @Expose
    val strArtistFanart3: String?,
    @SerializedName("strArtistFanart4")
    @Expose
    val strArtistFanart4: String?,
    @SerializedName("strArtistBanner")
    @Expose
    val strArtistBanner: String?,
    @SerializedName("strMusicBrainzID")
    @Expose
    val strMusicBrainzID: String?,
    @SerializedName("strISNIcode")
    @Expose
    val strISNIcode: String?,
    @SerializedName("strLastFMChart")
    @Expose
    val strLastFMChart: String?,
    @SerializedName("intCharted")
    @Expose
    val intCharted: String?,
    @SerializedName("strLocked")
    @Expose
    val strLocked: String?,

    )