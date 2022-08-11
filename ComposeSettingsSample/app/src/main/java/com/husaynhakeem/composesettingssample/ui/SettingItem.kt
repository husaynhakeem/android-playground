package com.husaynhakeem.composesettingssample.ui

import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier
            .heightIn(min = 56.dp),
    ) {
        content()
    }
}