package com.husaynhakeem.glancesample.widget.action

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.husaynhakeem.glancesample.util.toast

class DummyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            toast(context, "Received broadcast with intent $intent")
        }
    }
}