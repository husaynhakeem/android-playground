package com.husaynhakeem.datastoresample.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.husaynhakeem.datastoresample.ServiceLocator
import com.husaynhakeem.datastoresample.data.UserDataStore
import kotlinx.coroutines.launch

class LoginViewModel(private val userDataStore: UserDataStore) : ViewModel() {

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val id = generateIdFrom(username, password)
            val token = generateTokenFrom(username, password)
            userDataStore.storeUser(id, token)
        }
    }

    private fun generateIdFrom(username: String, password: String): Long {
        return (username.length + password.length) * (1..1_000_000).random().toLong()
    }

    private fun generateTokenFrom(username: String, password: String): String {
        val base = username + password
        val shuffledIndices = base.indices.shuffled()
        val token = StringBuilder()
        for (index in shuffledIndices) {
            token.append(base[index])
        }
        return token.toString()
    }

    class Factory(private val context: Context) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val userDataStore = ServiceLocator.getUserDataStore(context)
            return LoginViewModel(userDataStore) as T
        }
    }
}