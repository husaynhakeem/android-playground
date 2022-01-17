package com.husaynhakeem.glancesample.widget.size

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.LocalSize
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.SizeMode
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextDecoration
import androidx.glance.text.TextStyle
import com.husaynhakeem.glancesample.util.log
import com.husaynhakeem.glancesample.util.readable

/**
 * [SizeMode.Exact] causes the widget's UI to be refreshed every time its size changes, i.e every
 * time the user resizes it. This can result in not-so-great transitions between sizes if the UI
 * changes as well as buggy performance.
 */
class SizeExactWidget : GlanceAppWidget() {

    override val sizeMode: SizeMode = SizeMode.Exact

    @Composable
    override fun Content() {
        // Once you select this widget from the widgets selector, this should be called
        // immediately. This is also called every time the widget is resized.
        log("Size mode exact content invoked")

        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(text = "Exact", style = TextStyle(textDecoration = TextDecoration.Underline))

            val size = LocalSize.current
            Text(text = "${size.width.readable()} x ${size.height.readable()}")
        }
    }
}

class SizeExactWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget = SizeExactWidget()
}