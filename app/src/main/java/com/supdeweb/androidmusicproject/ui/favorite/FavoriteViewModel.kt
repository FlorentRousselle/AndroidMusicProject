package com.supdeweb.androidmusicproject.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supdeweb.androidmusicproject.data.model.AlbumModel
import com.supdeweb.androidmusicproject.data.tools.Status
import com.supdeweb.androidmusicproject.domain.features.album.ObserveFavoriteAlbumsUseCase
import com.supdeweb.androidmusicproject.ui.utils.DataStateEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val observeFavoriteAlbumsUseCase: ObserveFavoriteAlbumsUseCase,
) : ViewModel() {


    /**
     * album
     */
    private val favoriteAlbumFlow = MutableStateFlow(
        FavoriteAlbumState(
            currentStateEnum = DataStateEnum.IDLE,
        )
    )

    fun albumState(): Flow<FavoriteAlbumState> {
        return favoriteAlbumFlow.asStateFlow()
    }

    init {
        observeFavoriteAlbums()
    }

    private fun observeFavoriteAlbums() {
        viewModelScope.launch {
            favoriteAlbumFlow.emit(
                FavoriteAlbumState(
                    currentStateEnum = DataStateEnum.LOADING,
                )
            )
            observeFavoriteAlbumsUseCase().collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        favoriteAlbumFlow.emit(
                            FavoriteAlbumState(
                                currentStateEnum = DataStateEnum.SUCCESS,
                                albums = it.data
                            )
                        )
                    }
                    Status.ERROR -> {
                        favoriteAlbumFlow.emit(
                            FavoriteAlbumState(
                                currentStateEnum = DataStateEnum.ERROR,
                                errorMessage = it.message
                            )
                        )
                    }
                    Status.LOADING -> {
                        favoriteAlbumFlow.emit(
                            FavoriteAlbumState(
                                currentStateEnum = DataStateEnum.LOADING,
                            )
                        )
                    }
                }
            }
        }
    }
}

data class FavoriteAlbumState(
    val currentStateEnum: DataStateEnum,
    val albums: List<AlbumModel>? = null,
    val errorMessage: String? = null,
)