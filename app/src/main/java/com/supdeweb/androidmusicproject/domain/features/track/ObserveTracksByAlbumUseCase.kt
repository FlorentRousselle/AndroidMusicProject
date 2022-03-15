package com.supdeweb.androidmusicproject.domain.features.track

import com.supdeweb.androidmusicproject.data.model.TrackModel
import com.supdeweb.androidmusicproject.data.repository.TrackRepository
import com.supdeweb.androidmusicproject.data.tools.Resource
import kotlinx.coroutines.flow.Flow

class ObserveTracksByAlbumUseCase(
    private val trackRepo: TrackRepository,
) {
    operator fun invoke(albumId: String): Flow<Resource<List<TrackModel>?>> {
        return trackRepo.observeTracksByAlbum(albumId)
    }
}