package com.husaynhakeem.glancesample.widget

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.action.actionStartBroadcastReceiver
import androidx.glance.appwidget.action.actionStartService
import androidx.glance.layout.Column
import androidx.glance.text.Text
import com.husaynhakeem.glancesample.component.DummyActivity
import com.husaynhakeem.glancesample.component.DummyBroadcastReceiver
import com.husaynhakeem.glancesample.component.DummyService

internal val actionWidgetKey = ActionParameters.Key<String>("action-widget-key")

class ActionWidget : GlanceAppWidget() {

    @Composable
    override fun Content() {
        Column {
            Text(
                text = "Log on a click event",
                modifier = GlanceModifier.clickable(
                    onClick = actionRunCallback<LogActionCallback>(
                        parameters = actionParametersOf(
                            actionWidgetKey to "log event"
                        )
                    )
                )
            )

            Text(
                text = "Start an activity",
                modifier = GlanceModifier.clickable(
                    onClick = actionStartActivity<DummyActivity>(
                        parameters = actionParametersOf(
                            actionWidgetKey to "activity"
                        )
                    )
                )
            )

            Text(
                text = "Start a service",
                modifier = GlanceModifier.clickable(
                    onClick = actionStartService<DummyService>()
                )
            )

            Text(
                text = "Send a broadcast",
                modifier = GlanceModifier.clickable(
                    onClick = actionStartBroadcastReceiver<DummyBroadcastReceiver>()
                )
            )
        }
    }
}

class LogActionCallback : ActionCallback {
    override suspend fun onRun(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        Log.e("ActionWidget", "Item with id $glanceId and params $parameters clicked.")
    }
}


class ActionWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget = ActionWidget()
}