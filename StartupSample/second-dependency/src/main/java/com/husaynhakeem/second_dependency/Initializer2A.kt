package com.husaynhakeem.second_dependency

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.husaynhakeem.first_dependency.Initializer1A

class Initializer2A : Initializer<Initializer2A.Dependency> {

    override fun create(context: Context): Dependency {
        Log.d(TAG, "Initializer2A#create()")
        return Dependency()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(Initializer1A::class.java)
    }

    companion object {
        private const val TAG = "Initializer2A"
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