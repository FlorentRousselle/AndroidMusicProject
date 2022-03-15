package com.supdeweb.androidmusicproject.data.tools

import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Response
import retrofit2.awaitResponse

object ResponseHelper {

    fun <T> responseToResource(response: Response<T>): Resource<T> {
        return when {
            response.isSuccessful -> Resource.success(response.body())
            !response.isSuccessful -> Resource.error(response.message(), null)
            else -> Resource.loading(null)
        }
    }

    fun <T> responseToResource(response: Call<T>): Flow<Resource<T>> {
        return callbackFlow {
            val mResponse = response.awaitResponse()
            when {
                mResponse.isSuccessful -> trySend(Resource.success(mResponse.body()))
                !mResponse.isSuccessful -> trySend(Resource.error(mResponse.message(), null))
                else -> trySend(Resource.loading(null))
            }
            awaitClose { this.cancel() }
        }
    }
}