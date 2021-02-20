package com.husaynhakeem.datastoresample

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.husaynhakeem.datastoresample.data.NightModePreference
import com.husaynhakeem.datastoresample.data.UserDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel(
    private val nightModePreference: NightModePreference,
    userDataStore: UserDataStore
) : ViewModel() {

    val isNightModeEnabled = nightModePreference.isNightModeEnable().asLiveData()
    val isUserLoggedIn = userDataStore.getUser()
        .map { it.token.isNotBlank() }
        .asLiveData()

    fun toggleDayNightMode() {
        viewModelScope.launch {
            nightModePreference.setNightMode(!(isNightModeEnabled.value ?: false))
        }
    }

    class Factory(private val context: Context) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val nightModePreference = ServiceLocator.getNightModePreference(context)
            val userDataStore = ServiceLocator.getUserDataStore(context)
            return MainViewModel(nightModePreference, userDataStore) as T
        }
    }
}