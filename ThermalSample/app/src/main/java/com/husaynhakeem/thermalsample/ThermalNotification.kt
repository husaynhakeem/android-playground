package com.husaynhakeem.thermalsample

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

object ThermalNotification {

    private const val REQUEST_CODE = 20
    private const val CHANNEL_ID = "thermal-channel"

    fun create(context: Context, thermalStatus: ThermalStatus): Notification {
        createNotificationChannel(context)
        return createNotification(context, thermalStatus)
    }

    private fun createNotificationChannel(context: Context) {
        // Notification channels were introduced in Android 8
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }

        // If the channel already exists, return
        val manager = context.getSystemService(NotificationManager::class.java)
        val channelExists = manager.getNotificationChannel(CHANNEL_ID) != null
        if (channelExists) {
            return
        }

        // Create notification channel
        val channel = NotificationChannel(
            CHANNEL_ID,
            context.getString(R.string.notification_channel_label),
            NotificationManager.IMPORTANCE_DEFAULT
        )
        manager.createNotificationChannel(channel)
    }

    private fun createNotification(context: Context, thermalStatus: ThermalStatus): Notification {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, REQUEST_CODE, intent, 0)
        val message = thermalStatus.literal()
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setSmallIcon(R.drawable.ic_notification)
            .setTicker(message)
            .setContentIntent(pendingIntent)
            .build()
    }
}