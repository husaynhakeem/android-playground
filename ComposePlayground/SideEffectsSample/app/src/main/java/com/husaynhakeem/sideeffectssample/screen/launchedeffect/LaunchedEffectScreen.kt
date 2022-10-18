package com.husaynhakeem.sideeffectssample.screen.launchedeffect

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

const val LAUNCHED_EFFECT = "launched effect"

@Composable
fun LaunchedEffectScreen() {
    var timer by rememberSaveable { mutableStateOf(0) }
    var shouldTimerRun by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(key1 = shouldTimerRun) {
        if (!shouldTimerRun) {
            return@LaunchedEffect
        }
        while (true) {
            delay(1_000)
            timer++
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