package com.supdeweb.androidmusicproject.ui.details.album

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.supdeweb.androidmusicproject.domain.features.album.FetchAlbumDetailUseCase
import com.supdeweb.androidmusicproject.domain.features.album.ObserveAlbumDetailUseCase
import com.supdeweb.androidmusicproject.domain.features.album.UpdateFavoriteAlbumUseCase
import com.supdeweb.androidmusicproject.domain.features.track.FetchTracksByAlbumUseCase
import com.supdeweb.androidmusicproject.domain.features.track.ObserveTracksByAlbumUseCase


class AlbumDetailViewModelFactory(
    private val albumId: String,
    private val observeAlbumDetailUseCase: ObserveAlbumDetailUseCase,
    private val observeTracksByAlbumUseCase: ObserveTracksByAlbumUseCase,
    private val fetchTracksByAlbumUseCase: FetchTracksByAlbumUseCase,
    private val fetchAlbumDetailUseCase: FetchAlbumDetailUseCase,
    private val updateFavoriteAlbumUseCase: UpdateFavoriteAlbumUseCase,
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlbumDetailViewModel::class.java)) {
            return AlbumDetailViewModel(
                albumId,
                observeAlbumDetailUseCase,
                observeTracksByAlbumUseCase,
                fetchTracksByAlbumUseCase,
                fetchAlbumDetailUseCase,
                updateFavoriteAlbumUseCase,
            ) as T
        }
        throw IllegalArgumentException("Unknown AlbumDetailViewModel class")
    }
}