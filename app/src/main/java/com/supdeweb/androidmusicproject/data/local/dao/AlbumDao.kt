package com.supdeweb.androidmusicproject.data.local.dao

import androidx.room.*
import com.supdeweb.androidmusicproject.data.local.entity.AlbumEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlbum(album: AlbumEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllAlbums(albums: List<AlbumEntity>)

    @Query("SELECT * FROM album ORDER BY track ASC")
    fun getAlbums(): List<AlbumEntity>

    @Query("SELECT * FROM album WHERE id = :albumId")
    suspend fun getAlbumById(albumId: String): AlbumEntity

    @Query("SELECT * FROM album")
    fun observeAlbums(): Flow<List<AlbumEntity>>

    @Query("SELECT * FROM album WHERE id = :albumId")
    fun observeAlbumById(albumId: String): Flow<AlbumEntity>

    @Update
    suspend fun update(album: AlbumEntity): Int

    @Query("DELETE FROM album WHERE id = :albumId")
    suspend fun deleteById(albumId: String)

    @Query("DELETE FROM album")
    suspend fun deleteAll()
}