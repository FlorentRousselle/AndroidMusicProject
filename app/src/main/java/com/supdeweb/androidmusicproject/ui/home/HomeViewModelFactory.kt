package com.supdeweb.androidmusicproject.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.supdeweb.androidmusicproject.data.repository.AlbumRepository


class HomeViewModelFactory(
    private val albumRepo: AlbumRepository,
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(albumRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}