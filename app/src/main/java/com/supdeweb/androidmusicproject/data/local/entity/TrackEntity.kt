package com.supdeweb.androidmusicproject.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "track")
class TrackEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "track")
    val title: String?,

    @ColumnInfo(name = "track_artist_id")
    val artistId: String?,

    @ColumnInfo(name = "track_artist_name")
    val artistName: String?,

    @ColumnInfo(name = "track_album_id")
    val albumId: String?,

    @ColumnInfo(name = "style")
    val style: String?,

    @ColumnInfo(name = "score")
    val score: Double?,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "image_url")
    val imageUrl: String?,
)
