package com.husaynhakeem.first_dependency

import android.content.Context
import android.util.Log
import androidx.startup.Initializer

class Initializer1B : Initializer<Initializer1B.Dependency> {

    override fun create(context: Context): Dependency {
        Log.d(TAG, "Initializer1B#create()")
        return Dependency()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }

    companion object {
        private const val TAG = "Initializer1B"
    }

    class Dependency {
        init {
            isInitialized = true
        }

        companion object {
            var isInitialized = false
        }
    }
}