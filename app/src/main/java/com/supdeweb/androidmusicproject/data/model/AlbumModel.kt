package com.supdeweb.androidmusicproject.data.model

data class AlbumModel(
    val id: String,
    val albumName: String?,
    val artistId: String?,
    val artistName: String?,
    val style: String?,
    val sales: Int?,
    val year: String?,
    val score: Float?,
    val scoreVotes: Int?,
    val chartPlace: Int?,
    val description: String?,
    val imageUrl: String?,
    val isFavorite: Boolean,
)