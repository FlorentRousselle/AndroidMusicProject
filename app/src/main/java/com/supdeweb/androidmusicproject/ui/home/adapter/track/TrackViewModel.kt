package com.supdeweb.androidmusicproject.ui.home.adapter.track

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supdeweb.androidmusicproject.data.model.TrackModel
import com.supdeweb.androidmusicproject.data.repository.TrackRepository
import com.supdeweb.androidmusicproject.ui.utils.DataStateEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TrackViewModel(private val trackRepo: TrackRepository) : ViewModel() {
    /**
     *
     */
    private val tracksFlow = MutableStateFlow(
        TrackState(
            currentStateEnum = DataStateEnum.IDLE,
        )
    )

    fun trackState(): Flow<TrackState> {
        return tracksFlow.asStateFlow()
    }

    private val _toast = MutableStateFlow(String())
    fun toast(): Flow<String> {
        return _toast.asStateFlow()
    }

    init {
        observeAllTracks()
    }

    private fun observeAllTracks() {
        viewModelScope.launch {
            tracksFlow.emit(
                TrackState(
                    currentStateEnum = DataStateEnum.LOADING,
                )
            )

            trackRepo.observeFirstTenTracks().collect { tracks ->
                try {
                    if (tracks.isNotEmpty()) {
                        tracksFlow.emit(
                            TrackState(
                                currentStateEnum = DataStateEnum.SUCCESS,
                                tracks = tracks
                            )
                        )
                    } else {
                        tracksFlow.emit(
                            TrackState(
                                currentStateEnum = DataStateEnum.LOADING
                            )
                        )
                    }
                } catch (e: Exception) {
                    tracksFlow.emit(
                        TrackState(
                            currentStateEnum = DataStateEnum.ERROR,
                            tracks = null,
                            errorMessage = e.message
                        )
                    )
                }
            }


        }
    }
}

data class TrackState(
    val currentStateEnum: DataStateEnum,
    val tracks: List<TrackModel>? = null,
    val errorMessage: String? = null,
)
