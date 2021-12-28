package com.husaynhakeem.glancesample.widget

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
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
import androidx.glance.text.Text

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
                    modifier = GlanceModifier.clickable(
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
    override suspend fun onRun(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        Log.e("ListWidget", "Clicked on item with glanceId $glanceId and params $parameters.")
    }
}

class ListWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget = ListWidget()
}