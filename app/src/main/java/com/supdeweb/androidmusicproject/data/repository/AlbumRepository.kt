package com.supdeweb.androidmusicproject.data.repository

import android.content.Context
import androidx.annotation.WorkerThread
import com.supdeweb.androidmusicproject.data.local.dao.AlbumDao
import com.supdeweb.androidmusicproject.data.local.database.AndroidMusicProjectDatabase
import com.supdeweb.androidmusicproject.data.local.entity.AlbumEntity
import com.supdeweb.androidmusicproject.data.local.mapper.dtoAsEntity
import com.supdeweb.androidmusicproject.data.local.mapper.entitiesAsModel
import com.supdeweb.androidmusicproject.data.remote.ApiUtils
import com.supdeweb.androidmusicproject.data.remote.api.AlbumApi
import com.supdeweb.androidmusicproject.model.AlbumModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class AlbumRepository(
    private val albumDao: AlbumDao,
    private val albumApi: AlbumApi,
) {

    // OBSERVE
    fun observeAllAlbums(): Flow<List<AlbumModel>> {
        return albumDao.observeAlbums().map { it.entitiesAsModel() }
    }
    //GET

    /**
     * Remote get album
     */
    suspend fun fetchAlbums() {
        val albums = albumApi.getAlbums().albums
        insertAllAlbums(albums.dtoAsEntity())
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

    //DELETE
    suspend fun deleteAllAlbums() {
        albumDao.deleteAll()
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
                    val repo = AlbumRepository(db.albumDao, ApiUtils.albumApi)
                    instance = repo
                    INSTANCE = instance
                }
                return instance
            }

        }
    }
}