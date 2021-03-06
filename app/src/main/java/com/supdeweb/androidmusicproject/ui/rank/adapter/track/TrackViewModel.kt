package com.supdeweb.androidmusicproject.ui.rank.adapter.track

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supdeweb.androidmusicproject.data.model.TrackModel
import com.supdeweb.androidmusicproject.data.repository.TrackRepository
import com.supdeweb.androidmusicproject.data.tools.Status
import com.supdeweb.androidmusicproject.ui.utils.DataStateEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        getTrendingTracks()
    }

    fun getTrendingTracks() {
        trackRepo.fetchTrendingTracks { res ->
            viewModelScope.launch {
                tracksFlow.emit(
                    TrackState(
                        currentStateEnum = DataStateEnum.LOADING,
                    )
                )
                when (res.status) {
                    Status.SUCCESS -> {
                        tracksFlow.emit(
                            TrackState(
                                currentStateEnum = DataStateEnum.SUCCESS,
                                tracks = res.data?.sortedBy { it.chartPlace }
                            )
                        )
                    }
                    Status.ERROR -> {
                        tracksFlow.emit(
                            TrackState(
                                currentStateEnum = DataStateEnum.ERROR,
                                errorMessage = res.message
                            )
                        )
                    }
                    Status.LOADING -> {
                        tracksFlow.emit(
                            TrackState(
                                currentStateEnum = DataStateEnum.LOADING,
                            )
                        )
                    }
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
