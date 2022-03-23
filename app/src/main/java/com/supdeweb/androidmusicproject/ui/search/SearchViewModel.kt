package com.supdeweb.androidmusicproject.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supdeweb.androidmusicproject.data.model.AlbumModel
import com.supdeweb.androidmusicproject.data.model.ArtistModel
import com.supdeweb.androidmusicproject.data.tools.Status
import com.supdeweb.androidmusicproject.domain.features.album.FetchAlbumsByArtistNameUseCase
import com.supdeweb.androidmusicproject.domain.features.artist.FetchArtistByNameUseCase
import com.supdeweb.androidmusicproject.ui.utils.DataStateEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchViewModel(
    private val fetchArtistByNameUseCase: FetchArtistByNameUseCase,
    private val fetchAlbumsByArtistNameUseCase: FetchAlbumsByArtistNameUseCase,
) : ViewModel() {

    /**
     * artists
     */
    private val artistsFlow = MutableStateFlow(
        ArtistState(
            currentStateEnum = DataStateEnum.IDLE,
        )
    )

    fun artistState(): Flow<ArtistState> {
        return artistsFlow.asStateFlow()
    }

    /**
     * albums
     */
    private val albumsFlow = MutableStateFlow(
        AlbumState(
            currentStateEnum = DataStateEnum.IDLE,
        )
    )

    fun albumState(): Flow<AlbumState> {
        return albumsFlow.asStateFlow()
    }


    fun observeArtistsByName(artistName: String) {
        viewModelScope.launch {
            artistsFlow.emit(
                ArtistState(
                    currentStateEnum = DataStateEnum.LOADING,
                )
            )
            fetchArtistByNameUseCase(artistName).collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        artistsFlow.emit(
                            ArtistState(
                                currentStateEnum = DataStateEnum.SUCCESS,
                                artists = it.data
                            )
                        )
                        observeAlbumsByArtist(artistName)
                    }
                    Status.ERROR -> {
                        artistsFlow.emit(
                            ArtistState(
                                currentStateEnum = DataStateEnum.ERROR,
                                errorMessage = it.message
                            )
                        )
                    }
                    Status.LOADING -> {
                        artistsFlow.emit(
                            ArtistState(
                                currentStateEnum = DataStateEnum.LOADING,
                            )
                        )
                    }
                }
            }
        }
    }

    private fun observeAlbumsByArtist(artistName: String) {
        viewModelScope.launch {
            albumsFlow.emit(
                AlbumState(
                    currentStateEnum = DataStateEnum.LOADING,
                )
            )
            fetchAlbumsByArtistNameUseCase(artistName).collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        albumsFlow.emit(
                            AlbumState(
                                currentStateEnum = DataStateEnum.SUCCESS,
                                albums = it.data
                            )
                        )
                    }
                    Status.ERROR -> {
                        albumsFlow.emit(
                            AlbumState(
                                currentStateEnum = DataStateEnum.ERROR,
                                errorMessage = it.message
                            )
                        )
                    }
                    Status.LOADING -> {
                        albumsFlow.emit(
                            AlbumState(
                                currentStateEnum = DataStateEnum.LOADING,
                            )
                        )
                    }
                }
            }
        }
    }
}

data class ArtistState(
    val currentStateEnum: DataStateEnum,
    val artists: List<ArtistModel>? = null,
    val errorMessage: String? = null,
)

data class AlbumState(
    val currentStateEnum: DataStateEnum,
    val albums: List<AlbumModel>? = null,
    val errorMessage: String? = null,
)