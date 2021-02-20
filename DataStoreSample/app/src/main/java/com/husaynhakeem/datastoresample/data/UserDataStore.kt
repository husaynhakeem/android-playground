package com.husaynhakeem.datastoresample.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.createDataStore
import com.husaynhakeem.datastoresample.User
import kotlinx.coroutines.flow.Flow

class UserDataStore(context: Context) {

    private val dataStore: DataStore<User> =
        context.createDataStore(fileName = "user.pb", serializer = UserSerializer())

    fun getUser(): Flow<User> {
        return dataStore.data
    }

    suspend fun storeUser(id: Long, token: String) {
        dataStore.updateData { user ->
            user.toBuilder()
                .setId(id)
                .setToken(token)
                .build()
        }
    }
}