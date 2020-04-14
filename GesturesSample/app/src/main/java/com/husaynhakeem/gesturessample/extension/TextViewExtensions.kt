package com.husaynhakeem.gesturessample.extension

import android.annotation.SuppressLint
import android.widget.TextView


@SuppressLint("SetTextI18n")
fun TextView.addText(text: String) {
    setText("$text\n${this.text}")
}

fun TextView.overrideWithText(text: String) {
    setText(text)
}

fun TextView.clear() {
    text = ""
}