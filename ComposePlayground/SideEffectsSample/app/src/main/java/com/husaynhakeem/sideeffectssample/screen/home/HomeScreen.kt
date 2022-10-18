package com.husaynhakeem.sideeffectssample.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.husaynhakeem.sideeffectssample.screen.launchedeffect.LAUNCHED_EFFECT

const val HOME = "home"

@Composable
fun HomeScreen(
    navigateTo: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Item(
            route = LAUNCHED_EFFECT,
            navigateTo = navigateTo,
        )
    }
}

@Composable
private fun Item(
    route: String,
    navigateTo: (String) -> Unit,
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { navigateTo(route) },
        text = route,
    )
}