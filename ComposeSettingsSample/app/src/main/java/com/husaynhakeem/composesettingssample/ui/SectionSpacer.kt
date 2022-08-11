package com.husaynhakeem.composesettingssample.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SectionSpacer(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(height = 48.dp)
            .background(MaterialTheme.colors.onSurface.copy(alpha = 0.12f))
    )
}

@Preview
@Composable
fun Preview_SectionSpacer() {
    SectionSpacer()
}
