package com.husaynhakeem.barberapp.screen.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun Preview_PromoCard() {
    PromoCard(
        modifier = Modifier.fillMaxWidth(),
        label = "Accumulate 100 points and get a free visit",
        showPromo = {},
    )
}