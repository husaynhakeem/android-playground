package com.husaynhakeem.datastoresample.login

import android.content.Context
import androidx.lifecycle.*
import com.husaynhakeem.datastoresample.data.UserDataStore
import kotlinx.coroutines.launch

class LoginViewModel(private val userDataStore: UserDataStore) : ViewModel() {

    private val _openHomeScreen = MutableLiveData<Boolean>()
    val openHomeScreen: LiveData<Boolean>
        get() = _openHomeScreen

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val id = generateIdFrom(username, password)
            val token = generateTokenFrom(username, password)
            userDataStore.storeUser(id, token)
            _openHomeScreen.postValue(true)
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
            val userDataStore = UserDataStore(context)
            return LoginViewModel(userDataStore) as T
        }
    }
}