package com.supdeweb.androidmusicproject.domain.features.album

import com.supdeweb.androidmusicproject.data.repository.AlbumRepository
import kotlinx.coroutines.flow.Flow

class FetchAlbumDetailUseCase(
    private val albumRepo: AlbumRepository,
) {
    operator fun invoke(albumId: String): Flow<String?> {
        return albumRepo.fetchAlbumDetailBy(albumId)
    }
}