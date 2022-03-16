package com.supdeweb.androidmusicproject.data.remote

import com.supdeweb.androidmusicproject.data.remote.api.AlbumApi
import com.supdeweb.androidmusicproject.data.remote.api.ArtistApi
import com.supdeweb.androidmusicproject.data.remote.api.TrackApi

object ApiUtils {

    val albumApi: AlbumApi get() = NetworkManager.retrofit.create(AlbumApi::class.java)

    val trackApi: TrackApi get() = NetworkManager.retrofit.create(TrackApi::class.java)

    val artistApi: ArtistApi get() = NetworkManager.retrofit.create(ArtistApi::class.java)
}