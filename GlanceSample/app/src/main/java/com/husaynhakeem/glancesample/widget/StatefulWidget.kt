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
import androidx.glance.layout.*
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider

private val countPreferenceKey = intPreferencesKey("count-key")
private val countParamKey = ActionParameters.Key<Int>("count-key")

/**
 * A [GlanceAppWidget] can store data specific to its UI. To make a widget stateful, provide a
 * [GlanceStateDefinition], which defines where the widget's data is stored and how the underlying
 * data store is created. Glance provides [PreferencesGlanceStateDefinition], a state definition
 * based on [Preferences].
 *
 * To get the widget's current state when rendering its UI, use the local composition
 * [currentState], it returns a [Preferences] instance when you use a
 * [PreferencesGlanceStateDefinition], then use its APIs to query/save data.
 *
 * To update the widget's state, use [updateAppWidgetState], then call [GlanceAppWidget.update] to
 * refresh the widget's UI.
 */
class StatefulWidget : GlanceAppWidget() {

    override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

    @Composable
    override fun Content() {
        val prefs = currentState<Preferences>()
        val count = prefs[countPreferenceKey] ?: 0
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                text = "+",
                modifier = GlanceModifier.fillMaxWidth(),
                onClick = actionRunCallback<UpdateCountActionCallback>(
                    parameters = actionParametersOf(
                        countParamKey to (count + 1)
                    )
                )
            )
            Spacer(modifier = GlanceModifier.padding(8.dp))
            Text(
                text = count.toString(),
                modifier = GlanceModifier.fillMaxWidth(),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    color = ColorProvider(MaterialTheme.colors.onSurface),
                )
            )
            Spacer(modifier = GlanceModifier.padding(8.dp))
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