package com.supdeweb.androidmusicproject.domain.features.album

import com.supdeweb.androidmusicproject.data.local.mapper.asEntity
import com.supdeweb.androidmusicproject.data.model.AlbumModel
import com.supdeweb.androidmusicproject.data.repository.AlbumRepository
import com.supdeweb.androidmusicproject.data.tools.Resource
import com.supdeweb.androidmusicproject.data.tools.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class FetchAlbumDetailUseCase(
    private val albumRepo: AlbumRepository,
) {
    suspend operator fun invoke(albumId: String): Flow<Resource<AlbumModel?>> {
        val fetchedAlbum = albumRepo.fetchAlbumDetailBy(albumId).first()
        return when (fetchedAlbum.status) {
            Status.SUCCESS -> {
                fetchedAlbum.data?.asEntity()?.let { albumRepo.insertAlbum(it) }
                return albumRepo.observeAlbumById(albumId)
            }
            Status.ERROR -> flowOf(Resource.error(fetchedAlbum.message ?: "", null))
            Status.LOADING -> flowOf(Resource.loading(null))
        }
    }
}