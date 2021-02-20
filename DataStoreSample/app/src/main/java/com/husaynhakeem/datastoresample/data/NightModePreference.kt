package com.husaynhakeem.datastoresample.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NightModePreference(context: Context) {

    private val dataStore: DataStore<Preferences> =
        context.createDataStore(name = "data-store-sample-store")

    fun isNightModeEnable(): Flow<Boolean> {
        return dataStore.data
            .map { preferences ->
                preferences[KEY_NIGHT_MODE] ?: false
            }
    }

    suspend fun setNightMode(enable: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_NIGHT_MODE] = enable
        }
    }

    companion object {
        private val KEY_NIGHT_MODE = booleanPreferencesKey("key-night-mode-enabled")
    }
}