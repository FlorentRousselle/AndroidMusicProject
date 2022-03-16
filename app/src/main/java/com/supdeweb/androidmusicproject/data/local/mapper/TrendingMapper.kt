package com.supdeweb.androidmusicproject.data.local.mapper

import com.supdeweb.androidmusicproject.data.model.AlbumModel
import com.supdeweb.androidmusicproject.data.model.TrackModel
import com.supdeweb.androidmusicproject.data.remote.dto.track.TrendingDto

/**
 * Map DatabaseVideos to domain entities
 */
fun List<TrendingDto>.albumDtoAsModel(): List<AlbumModel> {
    return map {
        AlbumModel(
            id = it.idAlbum ?: throw IllegalAccessException("Must to have an album id"),
            albumName = it.strAlbum,
            artistId = it.idArtist,
            artistName = it.strArtist,
            style = null,
            sales = null,
            description = null,
            imageUrl = it.strAlbumThumb,
            isFavorite = false,
            chartPlace = it.intChartPlace?.toInt(),
            score = null,
            scoreVotes = null,
            year = null,
        )
    }
}

fun List<TrendingDto>.trackDtoAsModel(): List<TrackModel> {
    return map {
        TrackModel(
            id = it.idTrack ?: throw IllegalAccessException("Must to have a track id"),
            trackName = it.strTrack,
            artistId = it.idArtist,
            artistName = it.strArtist,
            style = null,
            description = null,
            imageUrl = it.strTrackThumb,
            chartPlace = it.intChartPlace?.toInt(),
            albumId = it.idAlbum,
            score = null,
        )
    }
}