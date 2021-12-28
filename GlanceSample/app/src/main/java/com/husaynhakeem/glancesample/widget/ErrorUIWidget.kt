package com.husaynhakeem.glancesample.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.LocalSize
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.SizeMode
import androidx.glance.text.Text
import com.husaynhakeem.glancesample.R
import kotlin.math.roundToInt

class ErrorUIWidget : GlanceAppWidget(errorUiLayout = R.layout.layout_widget_custom_error) {

    override val sizeMode = SizeMode.Exact

    @Composable
    override fun Content() {
        val size = LocalSize.current
        if (size.isSmallerThan(maxSize)) {
            Text(text = "Current size is ${size.width.readable()} x ${size.height.readable()}")
        } else {
            androidx.compose.material.Text(text = "error") // Not a glance composable, should error out
        }
    }

    private fun Dp.readable(): String {
        return "${value.roundToInt()}dp"
    }

    private fun DpSize.isSmallerThan(other: DpSize): Boolean {
        return this.width <= other.width && this.height <= other.height
    }

    private val maxSize = DpSize(200.dp, 200.dp)
}

class ErrorUIWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget = ErrorUIWidget()
}