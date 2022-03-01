package com.supdeweb.androidmusicproject.data.local.mapper

import com.supdeweb.androidmusicproject.data.local.entity.TrackEntity
import com.supdeweb.androidmusicproject.data.model.TrackModel
import com.supdeweb.androidmusicproject.data.remote.dto.TrackDto

/**
 * Map DatabaseVideos to domain entities
 */
fun List<TrackDto>.dtoAsEntity(): List<TrackEntity> {
    return map {
        TrackEntity(
            id = it.idTrack,
            title = it.strTrack,
            artistId = it.idArtist,
            artistName = it.strArtist,
            albumId = it.idAlbum,
            style = it.strStyle,
            score = it.intScore,
            description = it.strDescriptionFR,
            imageUrl = it.strTrackThumb,
            isFavorite = false
        )
    }
}

fun List<TrackDto>.dtoAsModel(): List<TrackModel> {
    return map {
        TrackModel(
            id = it.idTrack,
            title = it.strAlbum,
            artistId = it.idArtist,
            artistName = it.strArtist,
            style = it.strStyle,
            score = it.intScore,
            description = it.strDescriptionFR,
            imageUrl = it.strTrackThumb,
            isFavorite = false,
            albumId = it.idAlbum,
        )
    }
}

fun List<TrackModel>.modelAsEntity(): List<TrackEntity> {
    return map {
        TrackEntity(
            id = it.id,
            title = it.title,
            artistId = it.artistId,
            artistName = it.artistName,
            style = it.style,
            score = it.score,
            description = it.description,
            imageUrl = it.imageUrl,
            isFavorite = false,
            albumId = it.albumId,
        )
    }
}

fun List<TrackEntity>.entitiesAsModel(): List<TrackModel> {
    return map {
        TrackModel(
            id = it.id,
            title = it.title,
            artistId = it.artistId,
            artistName = it.artistName,
            style = it.style,
            score = it.score,
            description = it.description,
            imageUrl = it.imageUrl,
            isFavorite = it.isFavorite,
            albumId = it.albumId,
        )
    }
}

fun TrackEntity.asModel(): TrackModel {
    return TrackModel(
        id = this.id,
        title = this.title,
        artistId = this.artistId,
        artistName = this.artistName,
        style = this.style,
        score = this.score,
        description = this.description,
        imageUrl = this.imageUrl,
        isFavorite = this.isFavorite,
        albumId = this.albumId,
    )
}