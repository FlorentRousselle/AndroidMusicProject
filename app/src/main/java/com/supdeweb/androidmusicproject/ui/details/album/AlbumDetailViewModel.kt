package com.supdeweb.androidmusicproject.ui.details.album

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supdeweb.androidmusicproject.data.model.AlbumModel
import com.supdeweb.androidmusicproject.data.model.TrackModel
import com.supdeweb.androidmusicproject.data.tools.Status
import com.supdeweb.androidmusicproject.domain.features.album.FetchAlbumDetailUseCase
import com.supdeweb.androidmusicproject.domain.features.album.ObserveAlbumDetailUseCase
import com.supdeweb.androidmusicproject.domain.features.album.UpdateFavoriteAlbumUseCase
import com.supdeweb.androidmusicproject.domain.features.track.FetchTracksByAlbumUseCase
import com.supdeweb.androidmusicproject.domain.features.track.ObserveTracksByAlbumUseCase
import com.supdeweb.androidmusicproject.ui.utils.DataStateEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AlbumDetailViewModel(
    val albumId: String,
    private val observeAlbumDetailUseCase: ObserveAlbumDetailUseCase,
    private val observeTracksByAlbumUseCase: ObserveTracksByAlbumUseCase,
    private val fetchTracksByAlbumUseCase: FetchTracksByAlbumUseCase,
    private val fetchAlbumDetailUseCase: FetchAlbumDetailUseCase,
    private val updateFavoriteAlbumUseCase: UpdateFavoriteAlbumUseCase,
) : ViewModel() {

    /**
     * error message
     */
    private val errorMessage = MutableStateFlow<String?>(null)
    fun errorMessageState(): Flow<String?> {
        return errorMessage.asStateFlow()
    }

    /**
     * album
     */
    private val albumFlow = MutableStateFlow(
        AlbumDetailState(
            currentStateEnum = DataStateEnum.IDLE,
        )
    )

    fun albumState(): Flow<AlbumDetailState> {
        return albumFlow.asStateFlow()
    }

    /**
     * album
     */
    private val trackListFlow = MutableStateFlow(
        TrackListState(
            currentStateEnum = DataStateEnum.IDLE,
        )
    )

    fun trackListState(): Flow<TrackListState> {
        return trackListFlow.asStateFlow()
    }

    init {
        refreshData()
    }

    fun refreshData() {
        viewModelScope.launch {
            fetchAlbumDetailUseCase(albumId).collect {
                if (it != null) {
                    errorMessage.emit(it)
                }
            }
        }
        viewModelScope.launch {
            fetchTracksByAlbumUseCase(albumId).collect {
                if (it != null) {
                    errorMessage.emit(it)
                }
            }
        }
        observeAlbumDetail()
        observeTrackList()
    }

    private fun observeAlbumDetail() {
        viewModelScope.launch {
            albumFlow.emit(
                AlbumDetailState(
                    currentStateEnum = DataStateEnum.LOADING,
                )
            )
            observeAlbumDetailUseCase(albumId).collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        albumFlow.emit(
                            AlbumDetailState(
                                currentStateEnum = DataStateEnum.SUCCESS,
                                album = it.data
                            )
                        )
                    }
                    Status.ERROR -> {
                        albumFlow.emit(
                            AlbumDetailState(
                                currentStateEnum = DataStateEnum.ERROR,
                                errorMessage = it.message
                            )
                        )
                    }
                    Status.LOADING -> {
                        albumFlow.emit(
                            AlbumDetailState(
                                currentStateEnum = DataStateEnum.LOADING,
                            )
                        )
                    }
                }
            }
        }
    }

    private fun observeTrackList() {
        viewModelScope.launch {
            trackListFlow.emit(
                TrackListState(
                    currentStateEnum = DataStateEnum.LOADING,
                )
            )
            observeTracksByAlbumUseCase(albumId).collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        trackListFlow.emit(
                            TrackListState(
                                currentStateEnum = DataStateEnum.SUCCESS,
                                tracks = it.data
                            )
                        )
                    }
                    Status.ERROR -> {
                        trackListFlow.emit(
                            TrackListState(
                                currentStateEnum = DataStateEnum.ERROR,
                                errorMessage = it.message
                            )
                        )
                    }
                    Status.LOADING -> {
                        trackListFlow.emit(
                            TrackListState(
                                currentStateEnum = DataStateEnum.LOADING,
                            )
                        )
                    }
                }
            }
        }
    }

    fun updateFavoriteAlbum(isFavorite: Boolean) {
        viewModelScope.launch {
            updateFavoriteAlbumUseCase(albumId, isFavorite)
        }
    }
}

data class AlbumDetailState(
    val currentStateEnum: DataStateEnum,
    val album: AlbumModel? = null,
    val errorMessage: String? = null,
)

data class TrackListState(
    val currentStateEnum: DataStateEnum,
    val tracks: List<TrackModel>? = null,
    val errorMessage: String? = null,
)