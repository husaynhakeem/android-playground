package com.husaynhakeem.datastoresample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _isNightModeEnabled = MutableLiveData<Boolean>().apply { value = false }
    val isNightModeEnabled: LiveData<Boolean>
        get() = _isNightModeEnabled

    fun toggleDayNightMode() {
        val isEnabled = _isNightModeEnabled.value!!
        _isNightModeEnabled.value = !isEnabled
    }
}