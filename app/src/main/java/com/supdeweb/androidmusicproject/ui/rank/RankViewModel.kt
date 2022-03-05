package com.supdeweb.androidmusicproject.ui.rank

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supdeweb.androidmusicproject.data.repository.AlbumRepository
import com.supdeweb.androidmusicproject.data.repository.TrackRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RankViewModel(
    private val albumRepo: AlbumRepository,
    private val trackRepo: TrackRepository,
) : ViewModel() {

    /**
     * show toast message to user
     */
    private val _toast: MutableStateFlow<String?> = MutableStateFlow(null)
    fun toast(): Flow<String?> {
        return _toast.asStateFlow()
    }


    init {
        fetchAlbums()
        fetchTracks()
    }

    private fun fetchAlbums() {
        viewModelScope.launch {
            albumRepo.fetchAlbums().collect {
                val isFetch = it ?: false
                if (isFetch.not()) {
                    _toast.emit("Cannot fetch albumDtos")
                }
            }
        }
    }

    private fun fetchTracks() {
        viewModelScope.launch {
            trackRepo.fetchTracks().collect {
                val isFetch = it ?: false
                if (isFetch.not()) {
                    _toast.emit("Cannot fetch tracks")
                }
            }
        }
    }
}
