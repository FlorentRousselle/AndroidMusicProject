package com.supdeweb.androidmusicproject.domain.features.artist

import com.supdeweb.androidmusicproject.data.local.mapper.asEntity
import com.supdeweb.androidmusicproject.data.model.ArtistModel
import com.supdeweb.androidmusicproject.data.repository.ArtistRepository
import com.supdeweb.androidmusicproject.data.tools.Resource
import com.supdeweb.androidmusicproject.data.tools.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class FetchArtistDetailUseCase(
    private val artistRepo: ArtistRepository,
) {
    suspend operator fun invoke(artistId: String): Flow<Resource<ArtistModel?>> {
        val fetchedAlbum = artistRepo.fetchArtistDetailBy(artistId).first()
        return when (fetchedAlbum.status) {
            Status.SUCCESS -> {
                fetchedAlbum.data?.asEntity()?.let { artistRepo.insertArtist(it) }
                return artistRepo.observeArtistById(artistId)
            }
            Status.ERROR -> flowOf(Resource.error(fetchedAlbum.message ?: "", null))
            Status.LOADING -> flowOf(Resource.loading(null))
        }
    }
}