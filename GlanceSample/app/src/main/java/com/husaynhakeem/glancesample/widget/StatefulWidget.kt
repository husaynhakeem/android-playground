package com.husaynhakeem.glancesample.widget

import android.content.Context
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.currentState
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider

private val countPreferenceKey = intPreferencesKey("count-key")
private val countParamKey = ActionParameters.Key<Int>("count-key")

class StatefulWidget : GlanceAppWidget() {

    override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

    @Composable
    override fun Content() {
        val prefs = currentState<Preferences>()
        val count = prefs[countPreferenceKey] ?: 0
        Column {
            Button(
                text = "+",
                modifier = GlanceModifier.fillMaxWidth(),
                onClick = actionRunCallback<UpdateCountActionCallback>(
                    parameters = actionParametersOf(
                        countParamKey to (count + 1)
                    )
                )
            )
            Text(
                text = count.toString(),
                modifier = GlanceModifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    color = ColorProvider(MaterialTheme.colors.onSurface),
                )
            )
            Button(
                text = "-",
                modifier = GlanceModifier.fillMaxWidth(),
                onClick = actionRunCallback<UpdateCountActionCallback>(
                    parameters = actionParametersOf(
                        countParamKey to (count - 1)
                    )
                )
            )
        }
    }
}

class UpdateCountActionCallback : ActionCallback {
    override suspend fun onRun(context: Context, glanceId: GlanceId, parameters: ActionParameters) {

        // Get the new count passed as a parameter
        val count = requireNotNull(parameters[countParamKey]) {
            "Add $countParamKey parameter in the ActionParameters of this action."
        }

        // Update the count in the preferences data store
        updateAppWidgetState(
            context = context,
            definition = PreferencesGlanceStateDefinition,
            glanceId = glanceId
        ) { preferences ->
            preferences.toMutablePreferences()
                .apply {
                    this[countPreferenceKey] = count
                }
        }

        // Update the count in the widget
        StatefulWidget().update(context, glanceId)
    }
}

class StatefulWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget = StatefulWidget()
}