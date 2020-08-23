package com.husaynhakeem.instrumentationsample

import android.content.Intent
import android.os.Bundle
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.husaynhakeem.instrumentationsample.LogAssert.Companion.assertLog
import com.husaynhakeem.instrumentationsample.MainActivity.Companion.ON_CREATE
import com.husaynhakeem.instrumentationsample.MainActivity.Companion.ON_NEW_INTENT
import com.husaynhakeem.instrumentationsample.MainActivity.Companion.ON_PAUSE
import com.husaynhakeem.instrumentationsample.MainActivity.Companion.ON_POST_CREATE
import com.husaynhakeem.instrumentationsample.MainActivity.Companion.ON_RESTART
import com.husaynhakeem.instrumentationsample.MainActivity.Companion.ON_RESUME
import com.husaynhakeem.instrumentationsample.MainActivity.Companion.ON_SAVE_INSTANCE_STATE
import com.husaynhakeem.instrumentationsample.MainActivity.Companion.ON_START
import com.husaynhakeem.instrumentationsample.MainActivity.Companion.ON_STOP
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ActivityLifecycleTest {

    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    private val instrumentation = InstrumentationRegistry.getInstrumentation()

    @Test
    fun reachResume_whenLaunched() {
        rule.scenario.onActivity { activity ->
            assertLog(activity.logger.getMessages())
                .hasMessage(ON_CREATE)
                .hasMessage(ON_START)
                .hasMessage(ON_POST_CREATE)
                .hasMessage(ON_RESUME)
                .hasNoMoreMessages()
        }
    }

    @Test
    fun pause() {
        rule.scenario.onActivity { activity ->
            // Clear launch logs
            activity.logger.clear()

            // Pause
            instrumentation.callActivityOnPause(activity)

            assertLog(activity.logger.getMessages())
                .hasMessage(ON_PAUSE)
                .hasNoMoreMessages()
        }
    }

    @Test
    fun stop() {
        rule.scenario.onActivity { activity ->
            // Clear launch logs
            activity.logger.clear()

            // Stop
            instrumentation.callActivityOnStop(activity)

            assertLog(activity.logger.getMessages())
                .hasMessage(ON_STOP)
                .hasNoMoreMessages()
        }
    }

    @Test
    fun newIntent() {
        rule.scenario.onActivity { activity ->
            // Clear launch logs
            activity.logger.clear()

            // New intent
            val intent = Intent()
            instrumentation.callActivityOnNewIntent(activity, intent)

            assertLog(activity.logger.getMessages())
                .hasMessage(ON_NEW_INTENT)
                .hasMessage(intent.toString())
                .hasNoMoreMessages()
        }
    }

    @Test
    fun onSaveInstanceState() {
        rule.scenario.onActivity { activity ->
            // Clear launch logs
            activity.logger.clear()

            // Save instance state
            val state = Bundle()
            instrumentation.callActivityOnSaveInstanceState(activity, state)

            assertLog(activity.logger.getMessages())
                .hasMessage(ON_SAVE_INSTANCE_STATE)
                .hasMessage(state.toString())
                .hasNoMoreMessages()
        }
    }

    @Test
    fun pauseResume() {
        rule.scenario.onActivity { activity ->
            // Clear launch logs
            activity.logger.clear()

            // Pause then resume
            instrumentation.callActivityOnPause(activity)
            instrumentation.callActivityOnResume(activity)

            assertLog(activity.logger.getMessages())
                .hasMessage(ON_PAUSE)
                .hasMessage(ON_RESUME)
                .hasNoMoreMessages()
        }
    }

    @Test
    fun stopResume() {
        rule.scenario.onActivity { activity ->
            // Clear launch logs
            activity.logger.clear()

            // Stop then resume
            instrumentation.callActivityOnStop(activity)
            instrumentation.callActivityOnResume(activity)

            assertLog(activity.logger.getMessages())
                .hasMessage(ON_STOP)
                .hasMessage(ON_RESUME)
                .hasNoMoreMessages()
        }
    }

    @Test
    fun stopRestart() {
        rule.scenario.onActivity { activity ->
            // Clear launch logs
            activity.logger.clear()

            // Stop then resume
            instrumentation.callActivityOnStop(activity)
            instrumentation.callActivityOnRestart(activity)
            instrumentation.callActivityOnResume(activity)

            assertLog(activity.logger.getMessages())
                .hasMessage(ON_STOP)
                .hasMessage(ON_RESTART)
                .hasMessage(ON_RESUME)
                .hasNoMoreMessages()
        }
    }
}