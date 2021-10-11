package com.husaynhakeem.gradledependencyconfigssample

import android.util.Log
import com.husaynhakeem.compileonly.Logger

object LoggerProvider {

    private const val TAG = "LoggerProvider"

    @Throws(RuntimeException::class)
    fun get(): Logger {
        // Ideally, the consumer (the app module) should not use the class names of the concrete
        // implementation of the Logger class. Instead, a mechanism such as reflection could be
        // used to search in the classpath for any subclasses of Logger, then initialize it.
        return loggerInstance("com.husaynhakeem.runtimeonly.RuntimeOnlyLogger")
            ?: loggerInstance("com.husaynhakeem.otherruntimeonly.OtherRuntimeOnlyLogger")
            ?: throw LoggerImplementationNotFoundException()
    }

    private fun loggerInstance(className: String): Logger? {
        return try {
            val instance = Class.forName(className).newInstance()
            Log.d(TAG, "New logger instance of type [$className] initialized")
            instance as? Logger
        } catch (exception: ClassNotFoundException) {
            Log.i(TAG, "$className was not found")
            null
        }
    }
}