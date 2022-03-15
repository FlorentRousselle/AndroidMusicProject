package com.supdeweb.androidmusicproject.domain.features.album

import com.supdeweb.androidmusicproject.data.model.AlbumModel
import com.supdeweb.androidmusicproject.data.repository.AlbumRepository
import com.supdeweb.androidmusicproject.data.tools.Resource
import kotlinx.coroutines.flow.Flow


class ObserveAlbumDetailUseCase(
    private val albumRepo: AlbumRepository,
) {

    operator fun invoke(albumId: String): Flow<Resource<AlbumModel>> {
        return albumRepo.observeAlbumById(albumId)
    }
}