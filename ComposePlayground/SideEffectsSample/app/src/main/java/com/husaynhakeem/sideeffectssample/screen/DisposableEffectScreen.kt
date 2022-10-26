package com.husaynhakeem.sideeffectssample.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.husaynhakeem.sideeffectssample.log


const val DISPOSABLE_EFFECT = "Disposable effect"

@Composable
fun DisposableEffectScreen() {
    val lifecycleOwner by remember { mutableStateOf(FakeLifecycleOwner()) }
    var isLifecycleStarted by remember { mutableStateOf(false) }

    LifecycleObserverEffect(lifecycleOwner = lifecycleOwner)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Button(onClick = {
            if (isLifecycleStarted) {
                lifecycleOwner.stop()
            } else {
                lifecycleOwner.start()
            }
            isLifecycleStarted = !isLifecycleStarted
        }) {
            Text(text = if (isLifecycleStarted) "Stop lifecycle" else "Start lifecycle")
        }
    }
}

@Composable
private fun LifecycleObserverEffect(lifecycleOwner: LifecycleOwner) {
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                log("Disposable effect - Lifecycle started")
            } else if (event == Lifecycle.Event.ON_STOP) {
                log("Disposable effect - Lifecycle stopped")
            }
        }

        log("Disposable effect - Adding lifecycle observer")
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            log("Disposable effect - Removing lifecycle observer")
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

private class FakeLifecycleOwner : LifecycleOwner {

    private val lifecycleRegistry = LifecycleRegistry(this)

    init {
        lifecycleRegistry.currentState = Lifecycle.State.INITIALIZED
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
    }

    fun start() {
        check(lifecycleRegistry.currentState == Lifecycle.State.CREATED) { "Invalid state transition." }
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
    }

    fun stop() {
        check(lifecycleRegistry.currentState == Lifecycle.State.STARTED) { "Invalid state transition." }
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }
}
