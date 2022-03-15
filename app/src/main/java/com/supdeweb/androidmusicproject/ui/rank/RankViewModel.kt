package com.supdeweb.androidmusicproject.ui.rank

import androidx.lifecycle.ViewModel
import com.supdeweb.androidmusicproject.data.repository.AlbumRepository
import com.supdeweb.androidmusicproject.data.repository.TrackRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

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

    }

}
