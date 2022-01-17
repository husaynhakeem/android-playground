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
 * [SizeMode.Single] is the default size mode. This mode results in [GlanceAppWidget.content()]
 * only being called once with the minimum supported size defined as part of the widget's metadata.
 * If the available space for the widget changes, i.e. the user resizes the widget, the widget is
 * not redrawn (the content is not refreshed).
 */
class SizeSingleWidget : GlanceAppWidget() {

    override val sizeMode: SizeMode = SizeMode.Single

    @Composable
    override fun Content() {
        // Once you select this widget from the widgets selector, this should be called only once
        // immediately.
        log("Size mode single content invoked")

        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(text = "Single", style = TextStyle(textDecoration = TextDecoration.Underline))

            val size = LocalSize.current
            Text(text = "${size.width.readable()} x ${size.height.readable()}")
        }
    }
}

class SizeSingleWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget = SizeSingleWidget()
}