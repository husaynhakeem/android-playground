package com.husaynhakeem.instrumentationsample

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.husaynhakeem.instrumentationsample.LogAssert.Companion.assertLog
import com.husaynhakeem.instrumentationsample.MainActivity.Companion.ON_CREATE
import com.husaynhakeem.instrumentationsample.MainActivity.Companion.ON_POST_CREATE
import com.husaynhakeem.instrumentationsample.MainActivity.Companion.ON_RESUME
import com.husaynhakeem.instrumentationsample.MainActivity.Companion.ON_START
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewActivityTest {

    private val instrumentation = InstrumentationRegistry.getInstrumentation()

    @Test
    fun newActivity() {

        // Launch MainActivity
        val intent = Intent(instrumentation.targetContext, MainActivity::class.java)
        intent.flags = FLAG_ACTIVITY_NEW_TASK
        val activity = instrumentation.startActivitySync(intent)

        assertThat(activity).isInstanceOf(MainActivity::class.java)
        assertLog((activity as MainActivity).logger.getMessages())
            .hasMessage(ON_CREATE)
            .hasMessage(ON_START)
            .hasMessage(ON_POST_CREATE)
            .hasMessage(ON_RESUME)
            .hasNoMoreMessages()
    }
}