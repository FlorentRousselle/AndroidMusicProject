package com.supdeweb.androidmusicproject.domain.features.artist

import com.supdeweb.androidmusicproject.data.repository.ArtistRepository

class UpdateFavoriteArtistUseCase(
    private val artistRepo: ArtistRepository,
) {
    suspend operator fun invoke(artistId: String, isFavorite: Boolean) {
        val artist = artistRepo.getArtistById(artistId)

        artistRepo.updateArtist(artist.copy(isFavorite = isFavorite))
    }
}