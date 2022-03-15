package com.supdeweb.androidmusicproject.domain.features.track

import com.supdeweb.androidmusicproject.data.repository.TrackRepository
import kotlinx.coroutines.flow.Flow

class FetchTracksByAlbumUseCase(
    private val trackRepo: TrackRepository,
) {
    operator fun invoke(albumId: String): Flow<String?> {
        return trackRepo.fetchTracksBy(albumId)
    }
}