package com.husaynhakeem.glancesample.widget

import android.content.Context
import android.os.Handler
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.layout.padding
import androidx.glance.text.Text
import com.husaynhakeem.glancesample.util.toast

/**
 * Glance provides several composables out of the box that are similar to the ones offered by
 * `androidx.compose.ui`. Use them inside [GlanceAppWidget.content] to compose the widget's UI, and
 * use their [GlanceModifier] to decorate/add behavior to them.
 */
class ListWidget : GlanceAppWidget() {

    private val items = listOf(
        "eggs",
        "milk",
        "yogurt",
        "oats",
        "chia seeds",
        "maple syrup",
        "strawberries",
        "bananas",
        "apples",
        "olive oil",
    )

    @Composable
    override fun Content() {
        LazyColumn {
            items(items) { item ->
                Text(
                    text = item,
                    modifier = GlanceModifier
                        .padding(vertical = 8.dp)
                        .clickable(
                            onClick = actionRunCallback<ListWidgetClickActionCallback>(
                                actionParametersOf(itemKey to item)
                            )
                        )
                )
            }
        }
    }

    private val itemKey = ActionParameters.Key<String>("itemKey")
}

class ListWidgetClickActionCallback : ActionCallback {

    private lateinit var handler: Handler

    override suspend fun onRun(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        getHandler(context).post {
            toast(context, "Clicked on item with params $parameters")
        }
    }

    private fun getHandler(context: Context): Handler {
        if (!::handler.isInitialized) {
            handler = Handler(context.mainLooper)
        }
        return handler
    }
}

class ListWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget = ListWidget()
}