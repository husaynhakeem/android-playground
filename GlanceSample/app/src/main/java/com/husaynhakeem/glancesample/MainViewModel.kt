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
    suspend fun getAppWidgets(context: Context): List<Widget> {
        val manager = GlanceAppWidgetManager(context)
        return AppWidgetManager.getInstance(context)
            .installedProviders
            .filter { widgetProviderInfo -> widgetProviderInfo.provider.packageName == context.packageName }
            .mapNotNull { widgetProviderInfo -> Class.forName(widgetProviderInfo.provider.className) }
            .filter { GlanceAppWidgetReceiver::class.java.isAssignableFrom(it) }
            .map { (it.newInstance() as GlanceAppWidgetReceiver).glanceAppWidget.javaClass }
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