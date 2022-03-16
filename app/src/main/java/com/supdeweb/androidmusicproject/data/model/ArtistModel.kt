package com.supdeweb.androidmusicproject.data.model

data class ArtistModel(
    val id: String,
    val name: String?,
    val country: String?,
    val genre: String?,
    val description: String?,
    val imageUrl: String?,
    val isFavorite: Boolean,
)
