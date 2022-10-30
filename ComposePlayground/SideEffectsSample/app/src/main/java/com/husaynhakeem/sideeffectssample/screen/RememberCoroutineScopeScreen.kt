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