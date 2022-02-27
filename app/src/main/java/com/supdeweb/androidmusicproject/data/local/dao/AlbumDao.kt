package com.supdeweb.androidmusicproject.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.supdeweb.androidmusicproject.data.local.entity.AlbumEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(album: AlbumEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAlbums(albums: List<AlbumEntity>)

    @Query("SELECT * FROM album ORDER BY title ASC")
    fun getAlbums(): List<AlbumEntity>

    @Query("SELECT * FROM album WHERE id = :albumId")
    fun getAlbumById(albumId: String): List<AlbumEntity>

    @Query("SELECT * FROM album ORDER BY title ASC")
    fun observeAlbums(): Flow<List<AlbumEntity>>

    @Query("DELETE FROM album WHERE id = :albumId")
    suspend fun deleteById(albumId: String)

    @Query("DELETE FROM album")
    suspend fun deleteAll()
}