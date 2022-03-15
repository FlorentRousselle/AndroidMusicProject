package com.supdeweb.androidmusicproject.data.remote.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.supdeweb.androidmusicproject.data.remote.dto.track.TrendingDto

data class TrendingResponse(
    @SerializedName("trending")
    @Expose
    val trending: List<TrendingDto>? = null,
)