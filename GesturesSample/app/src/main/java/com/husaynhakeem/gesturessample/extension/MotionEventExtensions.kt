package com.husaynhakeem.gesturessample.extension

import android.view.MotionEvent


fun MotionEvent.singleTouchDescription(): String {
    val eventLiteral = when (action) {
        MotionEvent.ACTION_UP -> "Up"
        MotionEvent.ACTION_DOWN -> "Down"
        MotionEvent.ACTION_MOVE -> "Move"
        MotionEvent.ACTION_CANCEL -> "Cancel"
        MotionEvent.ACTION_OUTSIDE -> "Outside"
        else -> ""
    }
    return if (eventLiteral.isEmpty()) {
        ""
    } else {
        "$eventLiteral action at (${x.round()}, ${y.round()})"
    }
}

fun MotionEvent.multiTouchDescription(): String {
    val eventLiteral = when (action) {
        MotionEvent.ACTION_UP -> "Up"
        MotionEvent.ACTION_DOWN -> "Down"
        MotionEvent.ACTION_MOVE -> "Move"
        MotionEvent.ACTION_CANCEL -> "Cancel"
        MotionEvent.ACTION_OUTSIDE -> "Outside"
        else -> ""
    }
    return if (eventLiteral.isEmpty()) {
        ""
    } else {
        "$eventLiteral action at (${x.round()}, ${y.round()})"
    }
}

fun MotionEvent.isNewGesture(): Boolean {
    return action == MotionEvent.ACTION_DOWN
}

fun Float.round(): Int {
    return this.toInt()
}