package com.supdeweb.androidmusicproject.domain.features.artist

import com.supdeweb.androidmusicproject.data.model.ArtistModel
import com.supdeweb.androidmusicproject.data.repository.ArtistRepository
import com.supdeweb.androidmusicproject.data.tools.Resource
import kotlinx.coroutines.flow.Flow

class ObserveFavoriteArtistsUseCase(
    private val artistRepo: ArtistRepository,
) {
    operator fun invoke(): Flow<Resource<List<ArtistModel>>> {
        return artistRepo.observeFavoriteArtists()
    }
}