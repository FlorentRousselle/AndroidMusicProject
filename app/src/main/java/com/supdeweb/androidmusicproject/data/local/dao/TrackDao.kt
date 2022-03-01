package com.supdeweb.androidmusicproject.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.supdeweb.androidmusicproject.data.local.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: TrackEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTracks(tracks: List<TrackEntity>)

    @Query("SELECT * FROM track ORDER BY track ASC")
    fun getTracks(): List<TrackEntity>

    @Query("SELECT * FROM track WHERE id = :trackId")
    fun getTrackById(trackId: String): List<TrackEntity>

    @Query("SELECT * FROM track")
    fun observeTracks(): Flow<List<TrackEntity>>

    @Query("SELECT * FROM track LIMIT 10")
    fun observeFirstTenTracks(): Flow<List<TrackEntity>>

    @Query("DELETE FROM track WHERE id = :trackId")
    suspend fun deleteById(trackId: String)

    @Query("DELETE FROM track")
    suspend fun deleteAll()
}