package com.supdeweb.androidmusicproject.domain.features.album

import com.supdeweb.androidmusicproject.data.model.AlbumModel
import com.supdeweb.androidmusicproject.data.repository.AlbumRepository
import com.supdeweb.androidmusicproject.data.tools.Resource
import kotlinx.coroutines.flow.Flow

class ObserveFavoriteAlbumsUseCase(
    private val albumRepo: AlbumRepository,
) {
    operator fun invoke(): Flow<Resource<List<AlbumModel>>> {
        return albumRepo.observeFavoriteAlbums()
    }
}