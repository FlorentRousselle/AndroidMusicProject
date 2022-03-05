package com.supdeweb.androidmusicproject.ui.rank

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.supdeweb.androidmusicproject.data.repository.AlbumRepository
import com.supdeweb.androidmusicproject.data.repository.TrackRepository


class RankViewModelFactory(
    private val albumRepo: AlbumRepository,
    private val trackRepo: TrackRepository,
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RankViewModel::class.java)) {
            return RankViewModel(albumRepo, trackRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}