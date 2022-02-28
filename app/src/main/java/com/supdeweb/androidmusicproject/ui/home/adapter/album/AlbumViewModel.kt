package com.supdeweb.androidmusicproject.ui.home.adapter.album

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supdeweb.androidmusicproject.data.model.AlbumModel
import com.supdeweb.androidmusicproject.data.repository.AlbumRepository
import com.supdeweb.androidmusicproject.ui.utils.DataStateEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AlbumViewModel(private val albumRepo: AlbumRepository) : ViewModel() {
    /**
     *
     */
    private val albumsFlow = MutableStateFlow(
        AlbumState(
            currentStateEnum = DataStateEnum.IDLE,
        )
    )

    fun albumState(): Flow<AlbumState> {
        return albumsFlow.asStateFlow()
    }

    private val _toast = MutableStateFlow(String())
    fun toast(): Flow<String> {
        return _toast.asStateFlow()
    }

    init {
        observeAllAlbums()
    }

    private fun observeAllAlbums() {
        viewModelScope.launch {
            albumsFlow.emit(
                AlbumState(
                    currentStateEnum = DataStateEnum.LOADING,
                )
            )

            albumRepo.observeFirstTenAlbums().collect { albums ->
                try {
                    if (albums.isNotEmpty()) {
                        albumsFlow.emit(
                            AlbumState(
                                currentStateEnum = DataStateEnum.SUCCESS,
                                albums = albums
                            )
                        )
                    } else {
                        albumsFlow.emit(
                            AlbumState(
                                currentStateEnum = DataStateEnum.LOADING
                            )
                        )
                    }
                } catch (e: Exception) {
                    albumsFlow.emit(
                        AlbumState(
                            currentStateEnum = DataStateEnum.ERROR,
                            albums = null,
                            errorMessage = e.message
                        )
                    )
                }
            }


        }
    }
}

data class AlbumState(
    val currentStateEnum: DataStateEnum,
    val albums: List<AlbumModel>? = null,
    val errorMessage: String? = null,
)
