package com.husaynhakeem.camera2sample

import android.util.Log

inline fun <reified T> T.logInfo(message: String) {
    Log.i(T::class.java.simpleName, message)
}

inline fun <reified T> T.logError(message: String) {
    Log.e(T::class.java.simpleName, message)
}