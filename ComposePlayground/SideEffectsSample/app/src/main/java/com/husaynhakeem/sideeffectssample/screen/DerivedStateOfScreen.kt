package com.husaynhakeem.sideeffectssample.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

const val DERIVED_STATE_OF = "Derived state of"

@Composable
fun DerivedStateOfScreen() {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val showButton by remember { derivedStateOf { listState.isScrolledToBottom() } }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
        ) {
            items(100) { index ->
                Text(
                    text = "$index",
                    modifier = Modifier
                        .padding(
                            horizontal = 32.dp,
                            vertical = 16.dp,
                        ),
                )
            }
        }

        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            visible = showButton
        ) {
            Button(
                onClick = {
                    coroutineScope.launch { listState.animateScrollToItem(0) }
                }
            ) {
                Text(text = "Scroll up")
            }
        }
    }
}

private fun LazyListState.isScrolledToBottom(): Boolean {
    return layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
}
