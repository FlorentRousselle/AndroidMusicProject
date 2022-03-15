package com.supdeweb.androidmusicproject.data.local.mapper.track

import com.supdeweb.androidmusicproject.data.local.entity.TrackEntity
import com.supdeweb.androidmusicproject.data.model.TrackModel
import com.supdeweb.androidmusicproject.data.remote.dto.track.TrackDto

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
        )
    }
}

fun List<TrackDto>.dtoAsModel(): List<TrackModel> {
    return map {
        TrackModel(
            id = it.idTrack,
            trackName = it.strTrack,
            artistId = it.idArtist,
            artistName = it.strArtist,
            style = it.strStyle,
            score = it.intScore,
            description = it.strDescriptionFR,
            imageUrl = it.strTrackThumb,
            albumId = it.idAlbum,
            chartPlace = null,
        )
    }
}

fun List<TrackModel>.modelAsEntity(): List<TrackEntity> {
    return map {
        TrackEntity(
            id = it.id,
            title = it.trackName,
            artistId = it.artistId,
            artistName = it.artistName,
            style = it.style,
            score = it.score,
            description = it.description,
            imageUrl = it.imageUrl,
            albumId = it.albumId,
        )
    }
}

fun List<TrackEntity>.entitiesAsModel(): List<TrackModel> {
    return map {
        TrackModel(
            id = it.id,
            trackName = it.title,
            artistId = it.artistId,
            artistName = it.artistName,
            style = it.style,
            score = it.score,
            description = it.description,
            imageUrl = it.imageUrl,
            albumId = it.albumId,
            chartPlace = null,
        )
    }
}

fun TrackEntity.asModel(): TrackModel {
    return TrackModel(
        id = this.id,
        trackName = this.title,
        artistId = this.artistId,
        artistName = this.artistName,
        style = this.style,
        score = this.score,
        description = this.description,
        imageUrl = this.imageUrl,
        albumId = this.albumId,
        chartPlace = null,
    )
}
