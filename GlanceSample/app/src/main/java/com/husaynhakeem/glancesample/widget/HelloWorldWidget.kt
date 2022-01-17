package com.husaynhakeem.glancesample.widget

import androidx.compose.runtime.Composable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.text.Text

/**
 * Building a -basic- Glance widget involves the following:
 * - Creating the widget, which extends [GlanceAppWidget].
 * - Creating the widget provider, which extends [GlanceAppWidgetReceiver].
 * - Defining the widget's metadata in `res/xml`.
 * - Registering the widget provider in `AndroidManifest.xml`.
 */
class HelloWorldWidget : GlanceAppWidget() {

    @Composable
    override fun Content() {
        Text(text = "Hello world!")
    }
}

class HelloWorldWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget = HelloWorldWidget()
}