package com.supdeweb.androidmusicproject.ui.details.artist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.supdeweb.androidmusicproject.domain.features.album.FetchAlbumsByArtistUseCase
import com.supdeweb.androidmusicproject.domain.features.artist.FetchArtistDetailUseCase
import com.supdeweb.androidmusicproject.domain.features.artist.UpdateFavoriteArtistUseCase
import com.supdeweb.androidmusicproject.domain.features.track.FetchTopTracksByArtistUseCase


class ArtistDetailViewModelFactory(
    private val artistId: String,
    private val fetchArtistDetailUseCase: FetchArtistDetailUseCase,
    private val fetchAlbumsByArtistUseCase: FetchAlbumsByArtistUseCase,
    private val fetchTopTracksByArtistUseCase: FetchTopTracksByArtistUseCase,
    private val updateFavoriteArtistUseCase: UpdateFavoriteArtistUseCase,
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArtistDetailViewModel::class.java)) {
            return ArtistDetailViewModel(
                artistId = artistId,
                fetchArtistDetailUseCase = fetchArtistDetailUseCase,
                fetchAlbumsByArtistUseCase = fetchAlbumsByArtistUseCase,
                updateFavoriteArtistUseCase = updateFavoriteArtistUseCase,
                fetchTopTracksByArtistUseCase = fetchTopTracksByArtistUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown AlbumDetailViewModel class")
    }
}