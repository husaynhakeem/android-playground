package com.husaynhakeem.datastoresample.home

import android.content.Context
import androidx.lifecycle.*
import com.husaynhakeem.datastoresample.ServiceLocator
import com.husaynhakeem.datastoresample.User
import com.husaynhakeem.datastoresample.data.UserDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel(private val userDataStore: UserDataStore) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    init {
        viewModelScope.launch {
            val user = userDataStore.getUser().first()
            _user.postValue(user)
        }
    }

    fun logout() {
        viewModelScope.launch {
            userDataStore.removeUser()
        }
    }

    class Factory(private val context: Context) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val userDataStore = ServiceLocator.getUserDataStore(context)
            return HomeViewModel(userDataStore) as T
        }
    }
}
