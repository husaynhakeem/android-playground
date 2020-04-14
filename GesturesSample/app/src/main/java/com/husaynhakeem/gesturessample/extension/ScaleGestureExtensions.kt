package com.husaynhakeem.gesturessample.extension

import android.view.ScaleGestureDetector


fun ScaleGestureDetector?.description(type: String): String {
    return when {
        this == null -> "Null scale gesture detector"
        type.isEmpty() -> "Scale event with factor ${this.scaleFactor}"
        else -> "$type Scale event with factor ${this.scaleFactor}"
    }
}