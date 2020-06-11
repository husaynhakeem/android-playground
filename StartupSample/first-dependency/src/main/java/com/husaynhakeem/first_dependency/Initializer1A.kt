package com.husaynhakeem.first_dependency

import android.content.Context
import android.util.Log
import androidx.startup.Initializer

class Initializer1A : Initializer<Initializer1A.Dependency> {

    override fun create(context: Context): Dependency {
        Log.d(TAG, "Initializer1A#create()")
        return Dependency()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }

    companion object {
        private const val TAG = "Initializer1A"
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