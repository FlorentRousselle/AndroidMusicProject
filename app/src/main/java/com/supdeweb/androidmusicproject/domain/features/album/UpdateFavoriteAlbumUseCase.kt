package com.supdeweb.androidmusicproject.domain.features.album

import com.supdeweb.androidmusicproject.data.repository.AlbumRepository

class UpdateFavoriteAlbumUseCase(
    private val albumRepo: AlbumRepository,
) {
    suspend operator fun invoke(albumId: String, isFavorite: Boolean) {
        val album = albumRepo.getAlbumById(albumId)

        albumRepo.updateAlbum(album.copy(isFavorite = isFavorite))
    }
}