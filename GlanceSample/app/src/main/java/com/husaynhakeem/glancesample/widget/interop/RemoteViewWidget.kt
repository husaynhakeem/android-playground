package com.husaynhakeem.glancesample.widget.interop

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.widget.RemoteViews
import android.widget.Toast
import com.husaynhakeem.glancesample.R

private const val REMOTE_BUTTON_CLICK_ACTION = "action-remote-button-click"
private const val REMOTE_BUTTON_CLICK_REQUEST_CODE = 23

class RemoteViewWidget(context: Context) :
    RemoteViews(context.packageName, R.layout.widget_remote_view) {

    init {
        setOnClickPendingIntent(R.id.remoteViewButton, getPendingIntent(context))
    }

    private fun getPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, RemoteViewWidgetProvider::class.java).apply {
            action = REMOTE_BUTTON_CLICK_ACTION
        }
        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            FLAG_IMMUTABLE
        } else {
            0
        }
        return PendingIntent.getBroadcast(
            context,
            REMOTE_BUTTON_CLICK_REQUEST_CODE,
            intent,
            flags
        )
    }
}

class RemoteViewWidgetProvider : AppWidgetProvider() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent?.action == REMOTE_BUTTON_CLICK_ACTION) {
            Handler(context.mainLooper).post {
                Toast.makeText(
                    context,
                    R.string.remote_view_button_on_click_message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            super.onReceive(context, intent)
        }
    }
}