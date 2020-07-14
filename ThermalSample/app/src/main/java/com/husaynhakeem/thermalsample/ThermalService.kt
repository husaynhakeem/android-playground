package com.husaynhakeem.thermalsample

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.Q)
class ThermalService : Service() {

    private lateinit var thermalStatusListener: PowerManager.OnThermalStatusChangedListener

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        displayInitialThermalStatus()
        registerThermalListener()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterThermalListener()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun registerThermalListener() {
        thermalStatusListener = PowerManager.OnThermalStatusChangedListener { thermalStatus ->
            displayThermalNotification(thermalStatus)
        }
        getPowerManager().addThermalStatusListener(thermalStatusListener)
    }

    private fun unregisterThermalListener() {
        if (::thermalStatusListener.isInitialized) {
            getPowerManager().removeThermalStatusListener(thermalStatusListener)
        }
    }

    private fun getPowerManager() = getSystemService(PowerManager::class.java)

    private fun displayInitialThermalStatus() {
        val thermalStatus = getPowerManager().currentThermalStatus
        displayThermalNotification(thermalStatus)
    }

    private fun displayThermalNotification(thermalStatus: Int) {
        val notification = ThermalNotification.create(this, thermalStatus)
        startForeground(NOTIFICATION_ID, notification)
    }

    companion object {
        private const val NOTIFICATION_ID = 20
    }
}