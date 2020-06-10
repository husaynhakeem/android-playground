package com.husaynhakeem.second_dependency

import android.content.Context
import android.util.Log
import androidx.startup.Initializer

class Initializer2C : Initializer<Initializer2C.Dependency> {

    override fun create(context: Context): Dependency {
        Log.d(TAG, "Initializer2C#create()")
        Thread.sleep(3_000)
        return Dependency()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(Initializer2B::class.java)
    }

    companion object {
        private const val TAG = "Initializer2C"
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