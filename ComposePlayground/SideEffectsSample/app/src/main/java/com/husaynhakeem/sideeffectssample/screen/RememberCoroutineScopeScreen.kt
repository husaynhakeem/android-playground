package com.husaynhakeem.sideeffectssample.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

const val REMEMBER_COROUTINE_SCOPE = "Remember coroutine scope"

/**
 * [rememberCoroutineScope] is a composable function that returns a coroutine scope. This scope is
 * bound to the composition where it's called.
 *
 * It allows running a coroutine outside a composable, but scoped to a composable so that it's
 * cancelled when the composable leaves composition.
 *
 * The same coroutine scope is returned across recompositions.
 */
@Composable
fun RememberCoroutineScopeScreen() {
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "An error occurred!",
                            actionLabel = "Action",
                            duration = SnackbarDuration.Long,
                        )
                    }
                }
            ) {
                Text(text = "Show error")
            }
        }
    }
}