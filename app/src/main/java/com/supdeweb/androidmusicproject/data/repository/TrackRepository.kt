package com.supdeweb.androidmusicproject.data.repository

import android.content.Context
import androidx.annotation.WorkerThread
import com.supdeweb.androidmusicproject.data.local.dao.TrackDao
import com.supdeweb.androidmusicproject.data.local.database.AndroidMusicProjectDatabase
import com.supdeweb.androidmusicproject.data.local.entity.TrackEntity
import com.supdeweb.androidmusicproject.data.local.mapper.track.dtoAsModel
import com.supdeweb.androidmusicproject.data.local.mapper.track.entitiesAsModel
import com.supdeweb.androidmusicproject.data.local.mapper.trackDtoAsModel
import com.supdeweb.androidmusicproject.data.model.TrackModel
import com.supdeweb.androidmusicproject.data.remote.ApiUtils
import com.supdeweb.androidmusicproject.data.remote.api.TrackApi
import com.supdeweb.androidmusicproject.data.remote.api.TrackListResponse
import com.supdeweb.androidmusicproject.data.remote.api.TrendingResponse
import com.supdeweb.androidmusicproject.data.tools.Resource
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TrackRepository(
    private val trackDao: TrackDao,
    private val trackApi: TrackApi,
) {

    // OBSERVE
    fun observeTracksByAlbum(albumId: String): Flow<Resource<List<TrackModel>>> {
        return trackDao.observeTracksByAlbum(albumId)
            .onStart { Resource.loading(null) }
            .map { Resource.success(it.entitiesAsModel()) }
    }


    //INSERT
    @WorkerThread
    suspend fun insertAllTracks(tracks: List<TrackEntity>) {
        trackDao.insertAllTracks(tracks)
    }


    //----------- REMOTE ---------------


    fun fetchTrendingTracks(resource: (Resource<List<TrackModel>?>) -> Unit) {
        trackApi.getTrendingTracks().enqueue(object : Callback<TrendingResponse> {
            override fun onResponse(
                call: Call<TrendingResponse>,
                response: Response<TrendingResponse>,
            ) {
                return resource(Resource.success(response.body()?.trending?.trackDtoAsModel()))
            }

            override fun onFailure(call: Call<TrendingResponse>, t: Throwable) {
                return resource(Resource.error(t.message ?: "Cannot fetch trending tracks", null))
            }
        })
    }

    fun fetchTracksBy(albumId: String): Flow<Resource<List<TrackModel>?>> {
        return callbackFlow {
            trackApi.getTracksByAlbum(albumId).enqueue(object : Callback<TrackListResponse> {
                override fun onResponse(
                    call: Call<TrackListResponse>,
                    response: Response<TrackListResponse>,
                ) {
                    trySend(Resource.success(response.body()?.tracks?.dtoAsModel()))
                }

                override fun onFailure(call: Call<TrackListResponse>, t: Throwable) {
                    trySend(Resource.error(t.message ?: "Cannot fetch tracks by album", null))
                }
            })
            awaitClose { this.cancel() }
        }
    }

    fun fetchTracksByArtistName(artistName: String): Flow<Resource<List<TrackModel>?>> {
        return callbackFlow {
            trackApi.getTopTracksByArtistName(artistName)
                .enqueue(object : Callback<TrackListResponse> {
                    override fun onResponse(
                        call: Call<TrackListResponse>,
                        response: Response<TrackListResponse>,
                    ) {
                        trySend(Resource.success(response.body()?.tracks?.dtoAsModel()))
                    }

                    override fun onFailure(call: Call<TrackListResponse>, t: Throwable) {
                        trySend(Resource.error(t.message ?: "Cannot fetch tracks by artist name",
                            null))
                    }
                })
            awaitClose { this.cancel() }
        }
    }


    companion object {
        @Volatile
        var INSTANCE: TrackRepository? = null

        fun getInstance(context: Context): TrackRepository {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    val db: AndroidMusicProjectDatabase =
                        AndroidMusicProjectDatabase.getInstance(context)
                    val repo = TrackRepository(
                        db.trackDao,
                        ApiUtils.trackApi,
                    )
                    instance = repo
                    INSTANCE = instance
                }
                return instance
            }

        }
    }
}