package com.husaynhakeem.datastoresample.home

import android.content.Context
import androidx.lifecycle.*
import com.husaynhakeem.datastoresample.User
import com.husaynhakeem.datastoresample.data.UserDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel(private val userDataStore: UserDataStore) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _openLoginScreen = MutableLiveData<Boolean>()
    val openLoginScreen: LiveData<Boolean>
        get() = _openLoginScreen

    init {
        viewModelScope.launch {
            val user = userDataStore.getUser().first()
            _user.postValue(user)
        }
    }

    fun logout() {
        viewModelScope.launch {
            userDataStore.removeUser()
            _openLoginScreen.postValue(true)
        }
    }

    class Factory(private val context: Context) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val userDataStore = UserDataStore(context)
            return HomeViewModel(userDataStore) as T
        }
    }
}
