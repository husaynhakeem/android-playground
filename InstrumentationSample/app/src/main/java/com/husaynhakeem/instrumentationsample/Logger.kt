package com.husaynhakeem.instrumentationsample

import android.util.Log

class Logger {

    private val messages = mutableListOf<String>()

    fun log(message: String) {
        Log.d(TAG, message)
        messages.add(message)
    }

    fun getMessages(): List<String> = messages.toList()

    fun clear() {
        messages.clear()
    }

    companion object {
        private const val TAG = "Logger"
    }
}