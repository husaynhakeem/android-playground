package com.husaynhakeem.gesturessample.extension

import android.view.MotionEvent


fun MotionEvent.singleTouchDescription(): String {
    val eventLiteral = when (action) {
        MotionEvent.ACTION_DOWN -> "Down"
        MotionEvent.ACTION_UP -> "Up"
        MotionEvent.ACTION_MOVE -> "Move"
        MotionEvent.ACTION_CANCEL -> "Cancel"
        MotionEvent.ACTION_OUTSIDE -> "Outside"
        else -> ""
    }
    return if (eventLiteral.isEmpty()) {
        ""
    } else {
        "$eventLiteral action at (${x.toInt()}, ${y.toInt()})"
    }
}

fun MotionEvent.multiTouchDescription(): String {
    val eventLiteral = when (actionMasked) {
        MotionEvent.ACTION_DOWN -> "Down"
        MotionEvent.ACTION_UP -> "Up"
        MotionEvent.ACTION_MOVE -> "Move"
        MotionEvent.ACTION_CANCEL -> "Cancel"
        MotionEvent.ACTION_OUTSIDE -> "Outside"
        MotionEvent.ACTION_POINTER_DOWN -> "Pointer down"
        MotionEvent.ACTION_POINTER_UP -> "Pointer up"
        else -> ""
    }
    return if (eventLiteral.isEmpty()) {
        ""
    } else {
        val stringBuilder = StringBuilder("$eventLiteral action")
        if (actionMasked == MotionEvent.ACTION_POINTER_DOWN || actionMasked == MotionEvent.ACTION_POINTER_UP) {
            stringBuilder.append(" (pointer id: ${actionIndex})")
        }
        stringBuilder.append(" {")
        for (i in 0 until pointerCount) {
            stringBuilder.append("\n\t\tPointer with id ${getPointerId(i)} at (${getX(i).toInt()}, ${getY(i).toInt()})")
        }
        stringBuilder.append("\n}")
        stringBuilder.toString()
    }
}

fun MotionEvent.isNewGesture(): Boolean {
    return action == MotionEvent.ACTION_DOWN
}

fun Float.round(): Int {
    return this.toInt()
}