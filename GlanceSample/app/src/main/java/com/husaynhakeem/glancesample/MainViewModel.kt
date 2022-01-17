package com.husaynhakeem.glancesample

import android.app.Application
import android.appwidget.AppWidgetManager
import android.content.Context
import androidx.compose.ui.unit.DpSize
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _widgets = MutableStateFlow<List<Widget>>(emptyList())
    val widgets = _widgets.asStateFlow()

    init {
        viewModelScope.launch {
            _widgets.emit(getAppWidgets(application))
        }
    }

    @Suppress("ConvertCallChainIntoSequence")
    private suspend fun getAppWidgets(context: Context): List<Widget> {
        val manager = GlanceAppWidgetManager(context)
        return AppWidgetManager.getInstance(context)
            // Get all the installed widget providers
            .installedProviders
            // Filter only this app's widgets providers
            .filter { widgetProviderInfo -> widgetProviderInfo.provider.packageName == context.packageName }
            // Get this app's widget provider classes
            .mapNotNull { widgetProviderInfo -> Class.forName(widgetProviderInfo.provider.className) }
            // Filter only glance widget providers
            .filter { GlanceAppWidgetReceiver::class.java.isAssignableFrom(it) }
            // Get each widget provider's widget
            .map { (it.newInstance() as GlanceAppWidgetReceiver).glanceAppWidget.javaClass }
            // Convert each widget to a UI model
            .map {
                val metadata = manager.getGlanceIds(it)
                    .map { id ->
                        WidgetMetadata(
                            id = id,
                            sizes = manager.getAppWidgetSizes(id),
                        )
                    }
                Widget(
                    className = it.simpleName,
                    metadata = metadata,
                )
            }
    }
}

data class Widget(
    val className: String,
    val metadata: List<WidgetMetadata>
)

data class WidgetMetadata(
    val id: GlanceId,
    val sizes: List<DpSize>
)