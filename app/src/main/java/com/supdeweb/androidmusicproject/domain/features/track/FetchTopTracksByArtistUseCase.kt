package com.supdeweb.androidmusicproject.domain.features.track

import com.supdeweb.androidmusicproject.data.model.TrackModel
import com.supdeweb.androidmusicproject.data.repository.ArtistRepository
import com.supdeweb.androidmusicproject.data.repository.TrackRepository
import com.supdeweb.androidmusicproject.data.tools.Resource
import com.supdeweb.androidmusicproject.data.tools.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class FetchTopTracksByArtistUseCase(
    private val trackRepo: TrackRepository,
    private val artistRepo: ArtistRepository,
) {
    suspend operator fun invoke(artistId: String): Flow<Resource<List<TrackModel>?>> {
        val artist = artistRepo.getArtistById(artistId)
        val fetchedTracks = trackRepo.fetchTracksByArtistName(artist.name ?: "")
        return when (fetchedTracks.first().status) {
            Status.SUCCESS -> fetchedTracks
            Status.ERROR -> flowOf(Resource.error(fetchedTracks.first().message ?: "", null))
            Status.LOADING -> flowOf(Resource.loading(null))
        }
    }
}