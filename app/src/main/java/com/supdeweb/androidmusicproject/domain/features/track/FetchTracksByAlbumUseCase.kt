package com.supdeweb.androidmusicproject.domain.features.track

import com.supdeweb.androidmusicproject.data.local.mapper.track.modelAsEntity
import com.supdeweb.androidmusicproject.data.model.TrackModel
import com.supdeweb.androidmusicproject.data.repository.TrackRepository
import com.supdeweb.androidmusicproject.data.tools.Resource
import com.supdeweb.androidmusicproject.data.tools.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class FetchTracksByAlbumUseCase(
    private val trackRepo: TrackRepository,
) {
    suspend operator fun invoke(albumId: String): Flow<Resource<List<TrackModel>>> {
        val fetchedTracks = trackRepo.fetchTracksBy(albumId).first()
        return when (fetchedTracks.status) {
            Status.SUCCESS -> {
                fetchedTracks.data?.modelAsEntity()?.let { trackRepo.insertAllTracks(it) }
                return trackRepo.observeTracksByAlbum(albumId)
            }
            Status.ERROR -> flowOf(Resource.error(fetchedTracks.message ?: "", null))
            Status.LOADING -> flowOf(Resource.loading(null))
        }
    }
}