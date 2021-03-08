package com.husaynhakeem.websocketsample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.husaynhakeem.websocketsample.network.BtcWebSocket

class MainViewModel(btcWebSocket: BtcWebSocket) : ViewModel() {

    val state = btcWebSocket.events.asLiveData()

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(BtcWebSocket()) as T
        }
    }
}