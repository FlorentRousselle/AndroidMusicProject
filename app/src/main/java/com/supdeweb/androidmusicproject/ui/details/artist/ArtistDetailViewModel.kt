package com.supdeweb.androidmusicproject.ui.details.artist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supdeweb.androidmusicproject.data.model.AlbumModel
import com.supdeweb.androidmusicproject.data.model.ArtistModel
import com.supdeweb.androidmusicproject.data.model.TrackModel
import com.supdeweb.androidmusicproject.data.tools.Status
import com.supdeweb.androidmusicproject.domain.features.album.FetchAlbumsByArtistUseCase
import com.supdeweb.androidmusicproject.domain.features.artist.FetchArtistDetailUseCase
import com.supdeweb.androidmusicproject.domain.features.artist.UpdateFavoriteArtistUseCase
import com.supdeweb.androidmusicproject.domain.features.track.FetchTopTracksByArtistUseCase
import com.supdeweb.androidmusicproject.ui.utils.DataStateEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ArtistDetailViewModel(
    val artistId: String,
    private val fetchArtistDetailUseCase: FetchArtistDetailUseCase,
    private val fetchAlbumsByArtistUseCase: FetchAlbumsByArtistUseCase,
    private val fetchTopTracksByArtistUseCase: FetchTopTracksByArtistUseCase,
    private val updateFavoriteArtistUseCase: UpdateFavoriteArtistUseCase,
) : ViewModel() {

    /**
     * error message
     */
    private val errorMessage = MutableStateFlow<String?>(null)
    fun errorMessageState(): Flow<String?> {
        return errorMessage.asStateFlow()
    }

    /**
     * artist
     */
    private val artistFlow = MutableStateFlow(
        ArtistDetailState(
            currentStateEnum = DataStateEnum.IDLE,
        )
    )

    fun artistState(): Flow<ArtistDetailState> {
        return artistFlow.asStateFlow()
    }

    /**
     * albums
     */
    private val albumsFlow = MutableStateFlow(
        AlbumsByArtistState(
            currentStateEnum = DataStateEnum.IDLE,
        )
    )

    fun albumsState(): Flow<AlbumsByArtistState> {
        return albumsFlow.asStateFlow()
    }

    /**
     * albums
     */
    private val tracksFlow = MutableStateFlow(
        TracksByArtistState(
            currentStateEnum = DataStateEnum.IDLE,
        )
    )

    fun tracksState(): Flow<TracksByArtistState> {
        return tracksFlow.asStateFlow()
    }

    init {
        refreshData()
    }

    fun refreshData() {
        observeArtistDetail()
        observeAlbumsByArtist()
    }

    /**
     *
     */
    private fun observeArtistDetail() {
        viewModelScope.launch {
            artistFlow.emit(
                ArtistDetailState(
                    currentStateEnum = DataStateEnum.LOADING,
                )
            )
            fetchArtistDetailUseCase(artistId).collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        artistFlow.emit(
                            ArtistDetailState(
                                currentStateEnum = DataStateEnum.SUCCESS,
                                artist = it.data
                            )
                        )
                        observeTopTracks()
                    }
                    Status.ERROR -> {
                        artistFlow.emit(
                            ArtistDetailState(
                                currentStateEnum = DataStateEnum.ERROR,
                                errorMessage = it.message
                            )
                        )
                    }
                    Status.LOADING -> {
                        artistFlow.emit(
                            ArtistDetailState(
                                currentStateEnum = DataStateEnum.LOADING,
                            )
                        )
                    }
                }
            }
        }
    }

    /**
     *
     */
    private fun observeAlbumsByArtist() {
        viewModelScope.launch {
            albumsFlow.emit(
                AlbumsByArtistState(
                    currentStateEnum = DataStateEnum.LOADING,
                )
            )
            fetchAlbumsByArtistUseCase(artistId).collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        albumsFlow.emit(
                            AlbumsByArtistState(
                                currentStateEnum = DataStateEnum.SUCCESS,
                                albums = it.data
                            )
                        )
                    }
                    Status.ERROR -> {
                        albumsFlow.emit(
                            AlbumsByArtistState(
                                currentStateEnum = DataStateEnum.ERROR,
                                errorMessage = it.message
                            )
                        )
                    }
                    Status.LOADING -> {
                        albumsFlow.emit(
                            AlbumsByArtistState(
                                currentStateEnum = DataStateEnum.LOADING,
                            )
                        )
                    }
                }
            }
        }
    }

    private fun observeTopTracks() {
        viewModelScope.launch {
            tracksFlow.emit(
                TracksByArtistState(
                    currentStateEnum = DataStateEnum.LOADING,
                )
            )
            fetchTopTracksByArtistUseCase(artistId).collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        tracksFlow.emit(
                            TracksByArtistState(
                                currentStateEnum = DataStateEnum.SUCCESS,
                                tracks = it.data
                            )
                        )
                    }
                    Status.ERROR -> {
                        tracksFlow.emit(
                            TracksByArtistState(
                                currentStateEnum = DataStateEnum.ERROR,
                                errorMessage = it.message
                            )
                        )
                    }
                    Status.LOADING -> {
                        tracksFlow.emit(
                            TracksByArtistState(
                                currentStateEnum = DataStateEnum.LOADING,
                            )
                        )
                    }
                }
            }
        }
    }


    fun updateFavoriteArtist(isFavorite: Boolean) {
        viewModelScope.launch {
            updateFavoriteArtistUseCase(artistId, isFavorite)
        }
    }


}

data class ArtistDetailState(
    val currentStateEnum: DataStateEnum,
    val artist: ArtistModel? = null,
    val errorMessage: String? = null,
)

data class AlbumsByArtistState(
    val currentStateEnum: DataStateEnum,
    val albums: List<AlbumModel>? = null,
    val errorMessage: String? = null,
)

data class TracksByArtistState(
    val currentStateEnum: DataStateEnum,
    val tracks: List<TrackModel>? = null,
    val errorMessage: String? = null,
)