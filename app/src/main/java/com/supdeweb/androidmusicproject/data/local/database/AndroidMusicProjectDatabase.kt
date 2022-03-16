package com.supdeweb.androidmusicproject.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.supdeweb.androidmusicproject.data.local.dao.AlbumDao
import com.supdeweb.androidmusicproject.data.local.dao.ArtistDao
import com.supdeweb.androidmusicproject.data.local.dao.TrackDao
import com.supdeweb.androidmusicproject.data.local.entity.AlbumEntity
import com.supdeweb.androidmusicproject.data.local.entity.ArtistEntity
import com.supdeweb.androidmusicproject.data.local.entity.TrackEntity


@Database(
    entities = [
        AlbumEntity::class,
        TrackEntity::class,
        ArtistEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AndroidMusicProjectDatabase : RoomDatabase() {

    abstract val albumDao: AlbumDao
    abstract val trackDao: TrackDao
    abstract val artistDao: ArtistDao

    companion object {

        @Volatile
        private var INSTANCE: AndroidMusicProjectDatabase? = null

        fun getInstance(context: Context): AndroidMusicProjectDatabase {
            synchronized(this) {
                var instance =
                    INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AndroidMusicProjectDatabase::class.java,
                        "android_music_project_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}