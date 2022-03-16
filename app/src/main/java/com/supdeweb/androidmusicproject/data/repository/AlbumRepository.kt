package com.supdeweb.androidmusicproject.data.repository

import android.content.Context
import androidx.annotation.WorkerThread
import com.supdeweb.androidmusicproject.data.local.dao.AlbumDao
import com.supdeweb.androidmusicproject.data.local.database.AndroidMusicProjectDatabase
import com.supdeweb.androidmusicproject.data.local.datastore.AndroidMusicDataStore
import com.supdeweb.androidmusicproject.data.local.entity.AlbumEntity
import com.supdeweb.androidmusicproject.data.local.mapper.*
import com.supdeweb.androidmusicproject.data.model.AlbumModel
import com.supdeweb.androidmusicproject.data.remote.ApiUtils
import com.supdeweb.androidmusicproject.data.remote.api.AlbumApi
import com.supdeweb.androidmusicproject.data.remote.api.GetAlbumDetailResponse
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

    fun observeAlbumById(albumId: String): Flow<Resource<AlbumModel>> {
        return albumDao.observeAlbumById(albumId)
            .onStart { Resource.loading(null) }
            .map { Resource.success(it.asModel()) }
    }

    fun observeAllAlbumsByArtist(artistId: String): Flow<Resource<List<AlbumModel>>> {
        return albumDao.observeAllAlbumsByArtist(artistId)
            .onStart { Resource.loading(null) }
            .map { Resource.success(it.entitiesAsModel()) }
    }

    fun observeFavoriteAlbums(): Flow<Resource<List<AlbumModel>>> {
        return albumDao.observeFavoriteAlbums()
            .onStart { Resource.loading(null) }
            .map { Resource.success(it.entitiesAsModel()) }
    }

    //GET
    suspend fun getAlbumById(albumId: String): AlbumModel {
        return albumDao.getAlbumById(albumId).asModel()
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

    //UPDATE
    suspend fun updateAlbum(album: AlbumModel) {
        albumDao.update(album.asEntity())
    }

    //DELETE
    suspend fun deleteAllAlbums() {
        albumDao.deleteAll()
    }


    //----------- REMOTE ---------------

    fun fetchTrendingAlbums(): Flow<Resource<List<AlbumModel>?>> {
        return callbackFlow {
            albumApi.getTrendingAlbums().enqueue(object : Callback<TrendingResponse> {
                override fun onResponse(
                    call: Call<TrendingResponse>,
                    response: Response<TrendingResponse>,
                ) {
                    trySend(Resource.success(response.body()?.trending?.albumDtoAsModel()))
                }

                override fun onFailure(call: Call<TrendingResponse>, t: Throwable) {
                    trySend(Resource.error(t.message ?: "Cannot fetch trending albums", null))
                }
            })
            awaitClose { this.cancel() }
        }
    }

    fun fetchAlbumDetailBy(albumId: String): Flow<Resource<AlbumModel?>> {
        return callbackFlow {
            albumApi.getAlbumDetail(albumId).enqueue(object : Callback<GetAlbumDetailResponse> {
                override fun onResponse(
                    call: Call<GetAlbumDetailResponse>,
                    response: Response<GetAlbumDetailResponse>,
                ) {
                    trySend(Resource.success(response.body()?.album?.first()?.asModel()))
                }

                override fun onFailure(call: Call<GetAlbumDetailResponse>, t: Throwable) {
                    trySend(Resource.error(t.message ?: "Cannot fetch album detail", null))
                }
            })
            awaitClose { this.cancel() }
        }
    }

    fun fetchAlbumsByArtist(artistId: String): Flow<Resource<List<AlbumModel>?>> {
        return callbackFlow {
            albumApi.getAlbumsByArtist(artistId).enqueue(object : Callback<GetAlbumDetailResponse> {
                override fun onResponse(
                    call: Call<GetAlbumDetailResponse>,
                    response: Response<GetAlbumDetailResponse>,
                ) {
                    trySend(Resource.success(response.body()?.album?.dtoAsModel()))
                }

                override fun onFailure(call: Call<GetAlbumDetailResponse>, t: Throwable) {
                    trySend(Resource.error(t.message ?: "Cannot fetch albums by artist", null))
                }
            })
            awaitClose { this.cancel() }
        }
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