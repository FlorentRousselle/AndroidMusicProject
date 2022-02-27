package com.supdeweb.androidmusicproject.data.remote

import com.supdeweb.androidmusicproject.data.remote.api.AlbumApi

object ApiUtils {

    val albumApi: AlbumApi get() = NetworkManager.retrofit.create(AlbumApi::class.java)
}