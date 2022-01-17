package com.husaynhakeem.glancesample.widget.action

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.husaynhakeem.glancesample.util.toast

class DummyService : Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        toast(
            applicationContext,
            "Service called with intent $intent, flags $flags and start id $startId"
        )
        return super.onStartCommand(intent, flags, startId)
    }
}