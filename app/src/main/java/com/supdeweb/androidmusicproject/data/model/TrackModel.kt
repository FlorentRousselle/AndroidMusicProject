package com.supdeweb.androidmusicproject.data.model

class TrackModel(
    val id: String,
    val title: String?,
    val artistId: String?,
    val artistName: String?,
    val albumId: String?,
    val style: String?,
    val score: Double?,
    val description: String?,
    val imageUrl: String?,
    val isFavorite: Boolean,
)
