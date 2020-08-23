package com.husaynhakeem.instrumentationsample

import android.app.Instrumentation.ActivityMonitor
import android.app.UiAutomation.ROTATION_FREEZE_90
import android.app.UiAutomation.ROTATION_UNFREEZE
import android.content.Intent
import android.content.IntentFilter
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ActivityMonitorTest {

    private val instrumentation = InstrumentationRegistry.getInstrumentation()

    @Test
    fun monitor_filterByClassName() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->

            // Create MainActivity monitor
            val monitor = ActivityMonitor(MainActivity::class.java.name, null, false)
            instrumentation.addMonitor(monitor)

            // Recreate MainActivity
            scenario.recreate()

            // Wait for new MainActivity instance
            val activity = instrumentation.waitForMonitorWithTimeout(monitor, TIMEOUT)
            assertThat(activity).isNotNull()
            assertThat(activity).isInstanceOf(MainActivity::class.java)
        }
    }

    @Test
    fun monitor_filterByIntentFilter() {

        // Create monitor with intent filter
        val monitor = ActivityMonitor(IntentFilter(ACTION), null, false)
        instrumentation.addMonitor(monitor)

        // Create MainActivity
        val intent = Intent(instrumentation.targetContext, MainActivity::class.java)
        intent.action = ACTION
        ActivityScenario.launch<MainActivity>(intent).use {

            // Wait for MainActivity instance
            val activity = instrumentation.waitForMonitorWithTimeout(monitor, TIMEOUT)
            assertThat(activity).isNotNull()
            assertThat(activity).isInstanceOf(MainActivity::class.java)
        }
    }

    @Test
    fun monitor_rotateActivity() {
        ActivityScenario.launch(MainActivity::class.java).use {

            // Create MainActivity monitor
            val monitor = ActivityMonitor(MainActivity::class.java.name, null, false)
            instrumentation.addMonitor(monitor)

            // Rotate MainActivity
            instrumentation.uiAutomation.setRotation(ROTATION_FREEZE_90)

            // Wait for new MainActivity instance
            val activity = instrumentation.waitForMonitorWithTimeout(monitor, TIMEOUT)
            assertThat(activity).isNotNull()
            assertThat(activity).isInstanceOf(MainActivity::class.java)

            // Unfreeze screen rotation for subsequent tests
            instrumentation.uiAutomation.setRotation(ROTATION_UNFREEZE)
        }
    }

    companion object {
        private const val TIMEOUT = 1_000L
        private const val ACTION = "any-action"
    }
}