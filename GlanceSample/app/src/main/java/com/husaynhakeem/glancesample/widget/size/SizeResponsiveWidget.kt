package com.husaynhakeem.glancesample.widget.size

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.LocalSize
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.SizeMode
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextDecoration
import androidx.glance.text.TextStyle
import com.husaynhakeem.glancesample.util.log
import com.husaynhakeem.glancesample.util.readable

/**
 * On Android 12:
 * [SizeMode.Responsive] provides a way to define a set of sizes the widget supports. Glance calls
 * [GlanceAppWidget.content()] with each of the provided sizes, then stores the corresponding UI in
 * memory. When the widget is rendered for the first time or after being resized, the system
 * selects the right UI to display depending on the widget's available space. It reuses the UI it
 * had previously stored in memory. This results in smoother transitions and better
 * performance.
 *
 * Prior to Android 12:
 * [SizeMode.Responsive] seems to behave similar to [SizeMode.Exact], each time the widget is
 * resized, its UI is refreshed by calling [GlanceAppWidget.content()].
 */
class SizeResponsiveWidget : GlanceAppWidget() {

    override val sizeMode: SizeMode = SizeMode.Responsive(
        setOf(SMALL_SQUARE, HORIZONTAL_RECTANGLE, BIG_SQUARE)
    )

    @Composable
    override fun Content() {
        // On Android 12, once you select this widget from the widgets selector, this should be
        // called 3 (the number of sizes passed to SizeMode.Responsive) times immediately.
        // Prior to Android 12, this is called whenever the widget is resized.
        log("Size mode responsive content invoked")

        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "Responsive", style = TextStyle(textDecoration = TextDecoration.Underline))

            val size = LocalSize.current
            Text(text = "${size.width.readable()} x ${size.height.readable()}")
        }
    }

    companion object {
        private val SMALL_SQUARE = DpSize(100.dp, 100.dp)
        private val HORIZONTAL_RECTANGLE = DpSize(250.dp, 100.dp)
        private val BIG_SQUARE = DpSize(250.dp, 250.dp)
    }
}

class SizeResponsiveWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget = SizeResponsiveWidget()
}