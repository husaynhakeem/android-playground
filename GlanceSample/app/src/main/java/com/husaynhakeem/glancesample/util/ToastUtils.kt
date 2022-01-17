package com.husaynhakeem.glancesample.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes


fun toast(context: Context, message: String) {
    Toast.makeText(
        context,
        message,
        Toast.LENGTH_SHORT
    ).show()
}

fun toast(context: Context, @StringRes messageResId: Int) {
    toast(context, context.getString(messageResId))
}