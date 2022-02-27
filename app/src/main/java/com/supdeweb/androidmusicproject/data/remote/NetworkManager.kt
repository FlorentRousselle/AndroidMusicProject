package com.supdeweb.androidmusicproject.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    private const val API_KEY = "523532"
    private const val BASE_URL = "https://theaudiodb.com/api/v1/json/${API_KEY}/"

    val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
}
