package com.supdeweb.androidmusicproject.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.supdeweb.androidmusicproject.data.local.datastore.AndroidMusicDataStore.Companion.ANDROID_MUSIC_DATASTORE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = ANDROID_MUSIC_DATASTORE)

class AndroidMusicDataStore(val context: Context) {

    suspend inline fun <reified T> storeValue(key: Preferences.Key<T>, value: Any) {
        context.dataStore.edit {
            it[key] = value as T
        }
    }

    inline fun <reified T> getValueObs(
        PreferencesKey: Preferences.Key<T>,
    ): Flow<T?> {
        return context.dataStore.data.map {
            it[PreferencesKey]
        }
    }

    suspend inline fun <reified T> getValue(
        PreferencesKey: Preferences.Key<T>,
    ): T? {
        return context.dataStore.data.map {
            it[PreferencesKey]
        }.first()
    }

    suspend fun clearAll() {
        context.dataStore.edit {
            it.clear()
        }
    }

    suspend inline fun <reified T> remove(key: Preferences.Key<T>) {
        context.dataStore.edit { pref ->
            pref.remove(key)
        }
    }

    companion object {
        const val ANDROID_MUSIC_DATASTORE = "android_music_datastore"
    }
}
