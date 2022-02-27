package com.supdeweb.androidmusicproject.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "album")
class AlbumEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "artist_id")
    val artistId: String?,

    @ColumnInfo(name = "style")
    val style: String?,

    @ColumnInfo(name = "sales")
    val sales: Int?,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "image_url")
    val imageUrl: String?,
)
