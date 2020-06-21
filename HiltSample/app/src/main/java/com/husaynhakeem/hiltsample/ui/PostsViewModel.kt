package com.husaynhakeem.hiltsample.ui

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.husaynhakeem.hiltsample.data.Post
import com.husaynhakeem.hiltsample.data.PostsDataSource
import com.husaynhakeem.hiltsample.di.IoDispatcher
import com.husaynhakeem.hiltsample.di.RemoteDatSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class PostsViewModel @ViewModelInject constructor(
    @RemoteDatSource private val dataSource: PostsDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    init {
        getPosts()
    }

    fun getPosts() {
        _state.value = State.Loading
        viewModelScope.launch(ioDispatcher) {
            try {
                val posts = dataSource.getPosts()
                _state.postValue(State.Posts(posts))
            } catch (exception: Exception) {
                _state.postValue(State.Error)
                Log.e(TAG, "getPosts error", exception)
            }
        }
    }

    sealed class State {
        object Loading : State()
        object Error : State()
        class Posts(val posts: List<Post>) : State()
    }

    companion object {
        private const val TAG = "PostsViewModel"
    }
}