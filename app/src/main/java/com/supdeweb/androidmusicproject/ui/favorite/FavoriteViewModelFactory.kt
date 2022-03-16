package com.supdeweb.androidmusicproject.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.supdeweb.androidmusicproject.domain.features.album.ObserveFavoriteAlbumsUseCase


class FavoriteViewModelFactory(
    private val observeFavoriteAlbumsUseCase: ObserveFavoriteAlbumsUseCase,
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(
                observeFavoriteAlbumsUseCase,
            ) as T
        }
        throw IllegalArgumentException("Unknown FavoriteViewModel class")
    }
}