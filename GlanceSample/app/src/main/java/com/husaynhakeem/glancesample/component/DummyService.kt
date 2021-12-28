package com.husaynhakeem.glancesample.component

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class DummyService : Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(
            "DummyService",
            "Service started with intent $intent, flags $flags and start id $startId"
        )
        return super.onStartCommand(intent, flags, startId)
    }
}