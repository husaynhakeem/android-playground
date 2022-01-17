package com.husaynhakeem.glancesample.widget.action

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.action.actionStartActivity
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.action.actionStartBroadcastReceiver
import androidx.glance.appwidget.action.actionStartService
import androidx.glance.layout.Column
import androidx.glance.layout.padding
import com.husaynhakeem.glancesample.util.log

internal val actionWidgetKey = ActionParameters.Key<String>("action-widget-key")

/**
 * Glance provides a simple way to handle user interactions with predefined actions:
 * - [actionRunCallback]: Action that executes an [ActionCallback], and allows passing typed key
 * value pairs as parameters.
 * - [actionStartActivity]: Action that launches the Activity defined by a [ComponentName], [Class]
 * or type, and allows passing typed key value pairs as parameters.
 * - [actionStartService]: Action that launches the background or foreground service defined by its
 * [ComponentName], [Class] or type.
 * - [actionStartBroadcastReceiver]: Action that launches the broadcast receiver defined by its
 * [ComponentName], [Class] or type.
 */
class ActionWidget : GlanceAppWidget() {

    @Composable
    override fun Content() {
        Column(
            modifier = GlanceModifier.padding(8.dp)
        ) {
            Button(
                text = "Log on a click event",
                onClick = actionRunCallback<LogActionCallback>(
                    parameters = actionParametersOf(
                        actionWidgetKey to "log event"
                    )
                )
            )

            Button(
                text = "Start an activity",
                onClick = actionStartActivity<DummyActivity>(
                    parameters = actionParametersOf(
                        actionWidgetKey to "activity"
                    )
                )
            )

            Button(
                text = "Start a service",
                onClick = actionStartService<DummyService>()
            )

            Button(
                text = "Send a broadcast",
                onClick = actionStartBroadcastReceiver<DummyBroadcastReceiver>()
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
        log("Item with id $glanceId and params $parameters clicked.")
    }
}


class ActionWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget = ActionWidget()
}