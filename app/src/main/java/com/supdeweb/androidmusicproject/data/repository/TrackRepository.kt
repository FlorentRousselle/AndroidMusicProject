package com.supdeweb.androidmusicproject.data.repository

import android.content.Context
import androidx.annotation.WorkerThread
import com.supdeweb.androidmusicproject.data.local.dao.TrackDao
import com.supdeweb.androidmusicproject.data.local.database.AndroidMusicProjectDatabase
import com.supdeweb.androidmusicproject.data.local.datastore.AndroidMusicDataStore
import com.supdeweb.androidmusicproject.data.local.datastore.PreferenceKeys
import com.supdeweb.androidmusicproject.data.local.entity.TrackEntity
import com.supdeweb.androidmusicproject.data.local.mapper.album.trackDtoAsModel
import com.supdeweb.androidmusicproject.data.local.mapper.track.dtoAsEntity
import com.supdeweb.androidmusicproject.data.local.mapper.track.entitiesAsModel
import com.supdeweb.androidmusicproject.data.model.TrackModel
import com.supdeweb.androidmusicproject.data.remote.ApiUtils
import com.supdeweb.androidmusicproject.data.remote.api.TrackApi
import com.supdeweb.androidmusicproject.data.remote.api.TrendingResponse
import com.supdeweb.androidmusicproject.data.tools.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class TrackRepository(
    private val trackDao: TrackDao,
    private val trackApi: TrackApi,
    private val datastore: AndroidMusicDataStore,
) {

    // OBSERVE
    fun observeAllTracks(): Flow<List<TrackModel>> {
        return trackDao.observeTracks().map { it.entitiesAsModel() }
    }

    fun observeFirstTenTracks(): Flow<List<TrackModel>> {
        return trackDao.observeFirstTenTracks().map { it.entitiesAsModel() }
    }

    /**
     * return true if tracks are already fetch
     */
    fun isTrackAlreadyFetchObs(): Flow<Boolean?> {
        return datastore.getValueObs(PreferencesKey = PreferenceKeys.IS_ALBUM_FETCH)
    }


    //GET

    /**
     * Remote get tracks, if tracks are null the call api is dead
     */
    suspend fun fetchTracks(): Flow<Boolean?> {
        val isAlreadyFetch =
            datastore.getValue(PreferencesKey = PreferenceKeys.IS_TRACK_FETCH) ?: false
        // if tracks are already fetch, don't call api
        if (isAlreadyFetch.not()) {
            val tracks = trackApi.getTracks().tracks
            tracks?.dtoAsEntity()?.let {
                insertAllTracks(it)
            }
            // keep mind that tracks are stocked to not call api
            setIsTrackFetch(!tracks.isNullOrEmpty())
        }
        return isTrackAlreadyFetchObs()
    }

    //INSERT
    @WorkerThread
    suspend fun insertTrack(track: TrackEntity) {
        trackDao.insertTrack(track)
    }

    @WorkerThread
    suspend fun insertAllTracks(tracks: List<TrackEntity>) {
        trackDao.insertAllTracks(tracks)
    }

    /**
     * memories the fetch call api of tracks
     */
    suspend fun setIsTrackFetch(isFetch: Boolean) {
        datastore.storeValue(PreferenceKeys.IS_TRACK_FETCH, isFetch)
    }

    //DELETE
    suspend fun deleteAllTracks() {
        trackDao.deleteAll()
    }


    //----------- REMOTE ---------------


    fun getTrendingTracks(resource: (Resource<List<TrackModel>?>) -> Unit) {
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


    companion object {
        @Volatile
        var INSTANCE: TrackRepository? = null

        fun getInstance(context: Context): TrackRepository {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    val db: AndroidMusicProjectDatabase =
                        AndroidMusicProjectDatabase.getInstance(context)
                    val datastore = AndroidMusicDataStore(context)
                    val repo = TrackRepository(
                        db.trackDao,
                        ApiUtils.trackApi,
                        datastore
                    )
                    instance = repo
                    INSTANCE = instance
                }
                return instance
            }

        }
    }
}