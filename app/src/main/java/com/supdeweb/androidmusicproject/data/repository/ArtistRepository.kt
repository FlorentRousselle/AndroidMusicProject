package com.supdeweb.androidmusicproject.data.repository

import android.content.Context
import androidx.annotation.WorkerThread
import com.supdeweb.androidmusicproject.data.local.dao.ArtistDao
import com.supdeweb.androidmusicproject.data.local.database.AndroidMusicProjectDatabase
import com.supdeweb.androidmusicproject.data.local.entity.ArtistEntity
import com.supdeweb.androidmusicproject.data.local.mapper.asEntity
import com.supdeweb.androidmusicproject.data.local.mapper.asModel
import com.supdeweb.androidmusicproject.data.local.mapper.dtoAsModel
import com.supdeweb.androidmusicproject.data.local.mapper.entitiesAsModel
import com.supdeweb.androidmusicproject.data.model.ArtistModel
import com.supdeweb.androidmusicproject.data.remote.ApiUtils
import com.supdeweb.androidmusicproject.data.remote.api.ArtistApi
import com.supdeweb.androidmusicproject.data.remote.api.ArtistDetailResponse
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
class ArtistRepository(
    private val artistDao: ArtistDao,
    private val artistApi: ArtistApi,
) {


    fun observeArtistById(artistId: String): Flow<Resource<ArtistModel>> {
        return artistDao.observeArtistById(artistId)
            .onStart { Resource.loading(null) }
            .map { Resource.success(it.asModel()) }
    }

    fun observeFavoriteArtists(): Flow<Resource<List<ArtistModel>>> {
        return artistDao.observeFavoriteArtists()
            .onStart { Resource.loading(null) }
            .map { Resource.success(it.entitiesAsModel()) }
    }

    //GET
    suspend fun getArtistById(artistId: String): ArtistModel {
        return artistDao.getArtistById(artistId).asModel()
    }


    //INSERT
    @WorkerThread
    suspend fun insertArtist(artist: ArtistEntity) {
        artistDao.insertArtist(artist)
    }

    //UPDATE
    suspend fun updateArtist(artist: ArtistModel) {
        artistDao.update(artist.asEntity())
    }


    //----------- REMOTE ---------------

    fun fetchArtistDetailBy(artistId: String): Flow<Resource<ArtistModel?>> {
        return callbackFlow {
            artistApi.getArtistDetail(artistId).enqueue(object : Callback<ArtistDetailResponse> {
                override fun onResponse(
                    call: Call<ArtistDetailResponse>,
                    response: Response<ArtistDetailResponse>,
                ) {
                    trySend(Resource.success(response.body()?.artists?.first()?.asModel()))
                }

                override fun onFailure(call: Call<ArtistDetailResponse>, t: Throwable) {
                    trySend(Resource.error(t.message ?: "Cannot fetch artist detail", null))
                }
            })
            awaitClose { this.cancel() }
        }
    }

    fun fetchArtistsByName(artistName: String): Flow<Resource<List<ArtistModel>?>> {
        return callbackFlow {
            artistApi.getArtistsByName(artistName).enqueue(object : Callback<ArtistDetailResponse> {
                override fun onResponse(
                    call: Call<ArtistDetailResponse>,
                    response: Response<ArtistDetailResponse>,
                ) {
                    trySend(Resource.success(response.body()?.artists?.dtoAsModel()))
                }

                override fun onFailure(call: Call<ArtistDetailResponse>, t: Throwable) {
                    trySend(Resource.error(t.message ?: "Cannot fetch artists by name", null))
                }
            })
            awaitClose { this.cancel() }
        }
    }


    companion object {
        @Volatile
        var INSTANCE: ArtistRepository? = null

        fun getInstance(context: Context): ArtistRepository {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    val db: AndroidMusicProjectDatabase =
                        AndroidMusicProjectDatabase.getInstance(context)
                    val repo = ArtistRepository(
                        db.artistDao,
                        ApiUtils.artistApi
                    )
                    instance = repo
                    INSTANCE = instance
                }
                return instance
            }

        }
    }
}