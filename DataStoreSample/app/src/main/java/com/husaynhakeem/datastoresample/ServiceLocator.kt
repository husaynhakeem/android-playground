package com.husaynhakeem.datastoresample

import android.content.Context
import com.husaynhakeem.datastoresample.data.NightModePreference
import com.husaynhakeem.datastoresample.data.UserDataStore

object ServiceLocator {

    private lateinit var nightModePreference: NightModePreference
    private lateinit var userDataStore: UserDataStore

    fun getNightModePreference(context: Context): NightModePreference {
        if (!::nightModePreference.isInitialized) {
            nightModePreference = NightModePreference(context)
        }
        return nightModePreference
    }

    fun getUserDataStore(context: Context): UserDataStore {
        if (!::userDataStore.isInitialized) {
            userDataStore = UserDataStore(context)
        }
        return userDataStore
    }
}