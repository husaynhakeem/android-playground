package com.husaynhakeem.sideeffectssample.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.husaynhakeem.sideeffectssample.log
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay

const val LAUNCHED_EFFECT = "Launched effect"

/**
 * [LaunchedEffect] is a composable function that can only be run inside other compsoables.
 * - Runs a suspend effect in a coroutine when entering the composition, and cancels it when it
 * leaves the composition.
 * - Run effects across recompositions.
 * - If recomposed with different keys, it will cancel the ongoing coroutine and launch the effect
 * in a new coroutine.
 */
@Composable
fun LaunchedEffectScreen() {
    var timer by rememberSaveable { mutableStateOf(0) }
    var shouldTimerRun by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(key1 = shouldTimerRun) {
        if (!shouldTimerRun) {
            return@LaunchedEffect
        }
        try {
            while (true) {
                delay(1_000)
                timer++
            }
        } catch (exception: CancellationException) {
            log("Launched effect job cancelled with $exception")
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = "${timer}s")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { shouldTimerRun = !shouldTimerRun }) {
            Text(text = if (shouldTimerRun) "Pause" else "Resume")
        }
    }
}