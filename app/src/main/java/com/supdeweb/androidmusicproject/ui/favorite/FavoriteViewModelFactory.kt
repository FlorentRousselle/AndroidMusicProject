package com.supdeweb.androidmusicproject.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.supdeweb.androidmusicproject.domain.features.album.ObserveFavoriteAlbumsUseCase
import com.supdeweb.androidmusicproject.domain.features.artist.ObserveFavoriteArtistsUseCase


class FavoriteViewModelFactory(
    private val observeFavoriteAlbumsUseCase: ObserveFavoriteAlbumsUseCase,
    private val observeFavoriteArtistsUseCase: ObserveFavoriteArtistsUseCase,
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(
                observeFavoriteAlbumsUseCase,
                observeFavoriteArtistsUseCase,
            ) as T
        }
        throw IllegalArgumentException("Unknown FavoriteViewModel class")
    }
}