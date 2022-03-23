package com.supdeweb.androidmusicproject.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.supdeweb.androidmusicproject.domain.features.album.FetchAlbumsByArtistNameUseCase
import com.supdeweb.androidmusicproject.domain.features.artist.FetchArtistByNameUseCase


class SearchViewModelFactory(
    private val fetchArtistByNameUseCase: FetchArtistByNameUseCase,
    private val fetchAlbumsByArtistNameUseCase: FetchAlbumsByArtistNameUseCase,
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(
                fetchArtistByNameUseCase,
                fetchAlbumsByArtistNameUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown SearchViewModel class")
    }
}