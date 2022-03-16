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
            artistName = it.strArtist,
            style = it.strStyle,
            sales = it.intSales?.toInt(),
            description = it.strDescriptionFR,
            imageUrl = it.strAlbumThumb,
            isFavorite = false,
            score = it.intScore?.toFloat(),
            scoreVotes = it.intScoreVotes?.toInt(),
            year = it.intYearReleased,
        )
    }
}

fun List<AlbumDto>.dtoAsModel(): List<AlbumModel> {
    return map {
        AlbumModel(
            id = it.idAlbum,
            albumName = it.strAlbum,
            artistId = it.idArtist,
            artistName = it.strArtist,
            style = it.strStyle,
            sales = it.intSales?.toInt(),
            description = it.strDescriptionFR,
            imageUrl = it.strAlbumThumb,
            isFavorite = false,
            chartPlace = null,
            score = it.intScore?.toFloat(),
            scoreVotes = it.intScoreVotes?.toInt(),
            year = it.intYearReleased,
        )
    }
}

fun List<AlbumModel>.modelAsEntity(): List<AlbumEntity> {
    return map {
        AlbumEntity(
            id = it.id,
            title = it.albumName,
            artistId = it.artistId,
            artistName = it.artistName,
            style = it.style,
            sales = it.sales,
            description = it.description,
            imageUrl = it.imageUrl,
            isFavorite = false,
            score = it.score,
            scoreVotes = it.scoreVotes,
            year = it.year,
        )
    }
}

fun List<AlbumEntity>.entitiesAsModel(): List<AlbumModel> {
    return map {
        AlbumModel(
            id = it.id,
            albumName = it.title,
            artistId = it.artistId,
            artistName = it.artistName,
            style = it.style,
            sales = it.sales,
            description = it.description,
            imageUrl = it.imageUrl,
            isFavorite = it.isFavorite,
            chartPlace = null,
            score = it.score,
            scoreVotes = it.scoreVotes,
            year = it.year,
        )
    }
}

fun AlbumEntity.asModel(): AlbumModel {
    return AlbumModel(
        id = this.id,
        albumName = this.title,
        artistId = this.artistId,
        artistName = this.artistName,
        style = this.style,
        sales = this.sales,
        description = this.description,
        imageUrl = this.imageUrl,
        isFavorite = this.isFavorite,
        chartPlace = null,
        score = this.score,
        scoreVotes = this.scoreVotes,
        year = this.year,
    )
}

fun AlbumModel.asEntity(): AlbumEntity {
    return AlbumEntity(
        id = this.id,
        title = this.albumName,
        artistId = this.artistId,
        artistName = this.artistName,
        style = this.style,
        sales = this.sales,
        description = this.description,
        imageUrl = this.imageUrl,
        isFavorite = this.isFavorite,
        score = this.score,
        scoreVotes = this.scoreVotes,
        year = this.year,
    )
}

fun AlbumDto.asEntity(): AlbumEntity {
    return AlbumEntity(
        id = this.idAlbum,
        title = this.strAlbum,
        artistId = this.idArtist,
        artistName = this.strArtist,
        style = this.strStyle,
        sales = this.intSales?.toInt(),
        description = this.strDescriptionFR,
        imageUrl = this.strAlbumThumb,
        isFavorite = false,
        score = this.intScore?.toFloat(),
        scoreVotes = this.intScoreVotes?.toInt(),
        year = this.intYearReleased,
    )
}

fun AlbumDto.asModel(): AlbumModel {
    return AlbumModel(
        id = this.idAlbum,
        albumName = this.strAlbum,
        artistId = this.idArtist,
        artistName = this.strArtist,
        style = this.strStyle,
        sales = this.intSales?.toInt(),
        description = this.strDescriptionFR,
        imageUrl = this.strAlbumThumb,
        isFavorite = false,
        score = this.intScore?.toFloat(),
        scoreVotes = this.intScoreVotes?.toInt(),
        chartPlace = null,
        year = this.intYearReleased,
    )
}