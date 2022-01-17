package com.husaynhakeem.glancesample.util

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import kotlin.math.roundToInt

fun Dp.readable(): String {
    return "${value.roundToInt()}dp"
}

fun DpSize.isSmallerThan(other: DpSize): Boolean {
    return this.width <= other.width && this.height <= other.height
}