package com.supdeweb.androidmusicproject.ui.home.adapter.track

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.supdeweb.androidmusicproject.data.repository.TrackRepository


class TrackViewModelFactory(
    private val trackRepo: TrackRepository,
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrackViewModel::class.java)) {
            return TrackViewModel(trackRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}