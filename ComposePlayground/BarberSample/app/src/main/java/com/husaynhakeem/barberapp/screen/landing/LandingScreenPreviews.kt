package com.husaynhakeem.barberapp.screen.landing

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun Preview_LandingScreen() {
    LandingScreen(
        modifier = Modifier.fillMaxSize(),
        onNextClicked = {},
    )
}
