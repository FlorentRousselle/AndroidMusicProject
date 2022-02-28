package com.supdeweb.androidmusicproject.data.local.mapper

import com.supdeweb.androidmusicproject.data.local.entity.AlbumEntity
import com.supdeweb.androidmusicproject.data.model.AlbumModel
import com.supdeweb.androidmusicproject.data.remote.dto.album.AlbumDto

/**
 * Map DatabaseVideos to domain entities
 */
fun List<AlbumDto>.dtoAsEntity(): List<AlbumEntity> {
    return map {
        AlbumEntity(
            id = it.idAlbum,
            title = it.strAlbum,
            artistId = it.idArtist,
            style = it.strStyle,
            sales = it.intSales?.toInt(),
            description = it.strDescriptionFR,
            imageUrl = it.strAlbumThumb,
            isFavorite = false,
        )
    }
}

fun List<AlbumDto>.dtoAsModel(): List<AlbumModel> {
    return map {
        AlbumModel(
            id = it.idAlbum,
            title = it.strAlbum,
            artistId = it.idArtist,
            style = it.strStyle,
            sales = it.intSales?.toInt(),
            description = it.strDescriptionFR,
            imageUrl = it.strAlbumThumb,
            isFavorite = false,
        )
    }
}

fun List<AlbumModel>.modelAsEntity(): List<AlbumEntity> {
    return map {
        AlbumEntity(
            id = it.id,
            title = it.title,
            artistId = it.artistId,
            style = it.style,
            sales = it.sales,
            description = it.description,
            imageUrl = it.imageUrl,
            isFavorite = false,
        )
    }
}

fun List<AlbumEntity>.entitiesAsModel(): List<AlbumModel> {
    return map {
        AlbumModel(
            id = it.id,
            title = it.title,
            artistId = it.artistId,
            style = it.style,
            sales = it.sales,
            description = it.description,
            imageUrl = it.imageUrl,
            isFavorite = it.isFavorite,
        )
    }
}

fun AlbumEntity.asModel(): AlbumModel {
    return AlbumModel(
        id = this.id,
        title = this.title,
        artistId = this.artistId,
        style = this.style,
        sales = this.sales,
        description = this.description,
        imageUrl = this.imageUrl,
        isFavorite = this.isFavorite,
    )
}