package com.supdeweb.androidmusicproject.domain.features.album

import com.supdeweb.androidmusicproject.data.local.mapper.modelAsEntity
import com.supdeweb.androidmusicproject.data.model.AlbumModel
import com.supdeweb.androidmusicproject.data.repository.AlbumRepository
import com.supdeweb.androidmusicproject.data.tools.Resource
import com.supdeweb.androidmusicproject.data.tools.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class FetchAlbumsByArtistUseCase(
    private val albumRepo: AlbumRepository,
) {
    suspend operator fun invoke(artist: String): Flow<Resource<List<AlbumModel>?>> {
        val fetchedAlbums = albumRepo.fetchAlbumsByArtistId(artist).first()
        return when (fetchedAlbums.status) {
            Status.SUCCESS -> {
                fetchedAlbums.data?.modelAsEntity()?.let {
                    it.map { album ->
                        albumRepo.insertAlbum(album)
                    }
                }
                return albumRepo.observeAllAlbumsByArtist(artist)
            }
            Status.ERROR -> flowOf(Resource.error(fetchedAlbums.message ?: "", null))
            Status.LOADING -> flowOf(Resource.loading(null))
        }
    }
}