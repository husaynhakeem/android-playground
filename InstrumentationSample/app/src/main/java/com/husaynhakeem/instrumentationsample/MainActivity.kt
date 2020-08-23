package com.husaynhakeem.instrumentationsample

import android.content.Intent
import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    @VisibleForTesting
    val logger by lazy { Logger() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logEvent(ON_CREATE)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        logEvent(ON_POST_CREATE)
    }

    override fun onStart() {
        super.onStart()
        logEvent(ON_START)
    }

    override fun onRestart() {
        super.onRestart()
        logEvent(ON_RESTART)
    }

    override fun onResume() {
        super.onResume()
        logEvent(ON_RESUME)
    }

    override fun onPause() {
        super.onPause()
        logEvent(ON_PAUSE)
    }

    override fun onStop() {
        super.onStop()
        logEvent(ON_STOP)
    }

    override fun onDestroy() {
        super.onDestroy()
        logEvent(ON_DESTROY)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        logEvent(ON_NEW_INTENT)
        logEvent(intent.toString())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        logEvent(ON_SAVE_INSTANCE_STATE)
        logEvent(outState.toString())
    }

    private fun logEvent(event: String) {
        logger.log(event)
    }

    companion object {
        const val ON_CREATE = "onCreate"
        const val ON_POST_CREATE = "onPostCreate"
        const val ON_START = "onStart"
        const val ON_RESTART = "onRestart"
        const val ON_RESUME = "onResume"
        const val ON_PAUSE = "onPause"
        const val ON_STOP = "onStop"
        const val ON_DESTROY = "onDestroy"
        const val ON_NEW_INTENT = "onNewIntent"
        const val ON_SAVE_INSTANCE_STATE = "onSaveInstanceState"
    }
}