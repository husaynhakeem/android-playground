package com.husaynhakeem.glancesample.util

import android.util.Log

const val TAG_PREFIX = "GLANCE-"

inline fun <reified T> T.log(message: String) {
    Log.d("${TAG_PREFIX}${T::class.java.simpleName}", message)
}