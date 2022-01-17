package com.husaynhakeem.glancesample.widget.action

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class DummyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(
            context,
            "Received broadcast with intent $intent",
            Toast.LENGTH_SHORT
        ).show()
    }
}