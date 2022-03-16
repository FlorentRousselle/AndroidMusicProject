package com.supdeweb.androidmusicproject.data.local.dao

import androidx.room.*
import com.supdeweb.androidmusicproject.data.local.entity.ArtistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtistDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArtist(artist: ArtistEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllArtists(artists: List<ArtistEntity>)

    @Query("SELECT * FROM artist WHERE id = :artistId")
    suspend fun getArtistById(artistId: String): ArtistEntity

    @Query("SELECT * FROM artist")
    fun observeArtists(): Flow<List<ArtistEntity>>

    @Query("SELECT * FROM artist WHERE id = :artistId")
    fun observeArtistById(artistId: String): Flow<ArtistEntity>

    @Update
    suspend fun update(artist: ArtistEntity): Int

    @Query("DELETE FROM artist WHERE id = :artistId")
    suspend fun deleteById(artistId: String)

    @Query("DELETE FROM artist")
    suspend fun deleteAll()
}