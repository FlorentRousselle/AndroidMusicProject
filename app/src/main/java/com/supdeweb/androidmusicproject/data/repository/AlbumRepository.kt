package com.supdeweb.androidmusicproject.data.repository

import android.content.Context
import androidx.annotation.WorkerThread
import com.supdeweb.androidmusicproject.data.local.dao.AlbumDao
import com.supdeweb.androidmusicproject.data.local.database.AndroidMusicProjectDatabase
import com.supdeweb.androidmusicproject.data.local.datastore.AndroidMusicDataStore
import com.supdeweb.androidmusicproject.data.local.datastore.PreferenceKeys
import com.supdeweb.androidmusicproject.data.local.entity.AlbumEntity
import com.supdeweb.androidmusicproject.data.local.mapper.album.albumDtoAsModel
import com.supdeweb.androidmusicproject.data.local.mapper.album.dtoAsEntity
import com.supdeweb.androidmusicproject.data.local.mapper.album.entitiesAsModel
import com.supdeweb.androidmusicproject.data.model.AlbumModel
import com.supdeweb.androidmusicproject.data.remote.ApiUtils
import com.supdeweb.androidmusicproject.data.remote.api.AlbumApi
import com.supdeweb.androidmusicproject.data.remote.api.TrendingResponse
import com.supdeweb.androidmusicproject.data.tools.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class AlbumRepository(
    private val albumDao: AlbumDao,
    private val albumApi: AlbumApi,
    private val datastore: AndroidMusicDataStore,
) {

    // OBSERVE
    fun observeAllAlbums(): Flow<List<AlbumModel>> {
        return albumDao.observeAlbums().map { it.entitiesAsModel() }
    }

    fun observeFirstTenAlbums(): Flow<List<AlbumModel>> {
        return albumDao.observeFirstTenAlbums().map { it.entitiesAsModel() }
    }

    /**
     * return true if albumDtos are already fetch
     */
    fun isAlbumAlreadyFetchObs(): Flow<Boolean?> {
        return datastore.getValueObs(PreferencesKey = PreferenceKeys.IS_ALBUM_FETCH)
    }


    //GET

    /**
     * Remote get albumDtos, if albumDtos are null the call api is dead
     */
    suspend fun fetchAlbums(): Flow<Boolean?> {
        val isAlreadyFetch =
            datastore.getValue(PreferencesKey = PreferenceKeys.IS_ALBUM_FETCH) ?: false
        // if albumDtos are already fetch, don't call api
        if (isAlreadyFetch.not()) {
            val albums = albumApi.getAlbums().albums
            albums?.dtoAsEntity()?.let {
                insertAllAlbums(it)
            }
            // keep mind that albumDtos are stocked to not call api
            setIsAlbumFetch(!albums.isNullOrEmpty())
        }
        return isAlbumAlreadyFetchObs()
    }

    //INSERT
    @WorkerThread
    suspend fun insertAlbum(album: AlbumEntity) {
        albumDao.insertAlbum(album)
    }

    @WorkerThread
    suspend fun insertAllAlbums(albums: List<AlbumEntity>) {
        albumDao.insertAllAlbums(albums)
    }

    /**
     * memories the fetch call api of albumDtos
     */
    suspend fun setIsAlbumFetch(isFetch: Boolean) {
        datastore.storeValue(PreferenceKeys.IS_ALBUM_FETCH, isFetch)
    }

    //DELETE
    suspend fun deleteAllAlbums() {
        albumDao.deleteAll()
    }


    //----------- REMOTE ---------------


    fun getTrendingAlbums(resource: (Resource<List<AlbumModel>?>) -> Unit) {
        albumApi.getTrendingAlbums().enqueue(object : Callback<TrendingResponse> {
            override fun onResponse(
                call: Call<TrendingResponse>,
                response: Response<TrendingResponse>,
            ) {
                return resource(Resource.success(response.body()?.trending?.albumDtoAsModel()))
            }

            override fun onFailure(call: Call<TrendingResponse>, t: Throwable) {
                return resource(Resource.error(t.message ?: "Cannot fetch trending album", null))
            }
        })
    }


    companion object {
        @Volatile
        var INSTANCE: AlbumRepository? = null

        fun getInstance(context: Context): AlbumRepository {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    val db: AndroidMusicProjectDatabase =
                        AndroidMusicProjectDatabase.getInstance(context)
                    val datastore = AndroidMusicDataStore(context)
                    val repo = AlbumRepository(
                        db.albumDao,
                        ApiUtils.albumApi,
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