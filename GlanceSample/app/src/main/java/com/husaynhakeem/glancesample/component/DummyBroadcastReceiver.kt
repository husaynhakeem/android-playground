package com.husaynhakeem.glancesample.component

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class DummyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("DummyBroadcastReceiver", "Received intent $intent")
    }
}