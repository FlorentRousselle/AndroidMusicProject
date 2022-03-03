package com.supdeweb.androidmusicproject.data.model

class AlbumModel(
    val id: String,
    val albumName: String?,
    val artistId: String?,
    val artistName: String?,
    val style: String?,
    val sales: Int?,
    val chartPlace: Int?,
    val description: String?,
    val imageUrl: String?,
    val isFavorite: Boolean,
)