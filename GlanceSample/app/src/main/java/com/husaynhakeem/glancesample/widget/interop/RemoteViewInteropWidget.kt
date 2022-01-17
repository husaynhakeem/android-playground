package com.husaynhakeem.glancesample.widget.interop

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.LocalContext
import androidx.glance.appwidget.AndroidRemoteViews
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text

/**
 * Widget that demonstrates the use of Glance UI components in interoperability with
 * [android.widget.RemoteViews]. You can add existing remote views to a Glance composition by
 * wrapping them in [AndroidRemoteViews]
 */
class RemoteViewInteropWidget : GlanceAppWidget() {

    @Composable
    override fun Content() {
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(text = "Above remote views")

            val context = LocalContext.current
            AndroidRemoteViews(remoteViews = RemoteViewWidget(context))

            Text(text = "Below remote views")
        }
    }
}

class RemoteViewInteropWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget = RemoteViewInteropWidget()
}