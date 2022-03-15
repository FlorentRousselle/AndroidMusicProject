package com.supdeweb.androidmusicproject.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "album")
class AlbumEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "track")
    val title: String?,

    @ColumnInfo(name = "album_artist_id")
    val artistId: String?,

    @ColumnInfo(name = "album_artist_name")
    val artistName: String?,

    @ColumnInfo(name = "style")
    val style: String?,

    @ColumnInfo(name = "sales")
    val sales: Int?,

    @ColumnInfo(name = "score")
    val score: Float?,

    @ColumnInfo(name = "score_votes")
    val scoreVotes: Int?,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "image_url")
    val imageUrl: String?,

    @ColumnInfo(name = "is_favorite_album")
    val isFavorite: Boolean,
)
