package com.husaynhakeem.menuhostsample

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager

fun Activity.hideSoftKeyBoard() {
    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager? ?: return
    if (imm.isAcceptingText) {
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}