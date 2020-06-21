package com.husaynhakeem.hiltsample.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.husaynhakeem.hiltsample.data.Post
import com.husaynhakeem.hiltsample.data.PostsDataSource
import com.husaynhakeem.hiltsample.di.FakeDataSource
import kotlinx.coroutines.launch

class PostsViewModel @ViewModelInject constructor(@FakeDataSource private val dataSource: PostsDataSource) :
    ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    init {
        getPosts()
    }

    private fun getPosts() {
        _state.value = State.Loading
        viewModelScope.launch {
            try {
                val posts = dataSource.getPosts()
                _state.postValue(State.Posts(posts))
            } catch (exception: Exception) {
                _state.postValue(State.Error)
            }
        }
    }

    sealed class State {
        object Loading : State()
        object Error : State()
        class Posts(val posts: List<Post>) : State()
    }
}