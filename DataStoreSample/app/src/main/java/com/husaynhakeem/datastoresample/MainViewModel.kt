package com.husaynhakeem.datastoresample

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStore: DataStore<Preferences> =
        application.createDataStore(name = NAME_DATA_STORE)

    val isNightModeEnabled = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                exception.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[KEY_NIGHT_MODE] ?: false
        }.asLiveData()

    private val _person = MutableLiveData<Person>()
    val person: LiveData<Person>
        get() = _person

    fun toggleDayNightMode() {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[KEY_NIGHT_MODE] = !(preferences[KEY_NIGHT_MODE] ?: false)
            }
        }
    }

    fun savePerson(person: Person) {
        // TODO: Implement save person
    }

    companion object {
        private const val NAME_DATA_STORE = "data-store-sample-store"
        private val KEY_NIGHT_MODE = booleanPreferencesKey("key-night-mode-enabled")
    }
}