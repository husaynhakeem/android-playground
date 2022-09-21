package com.husaynhakeem.barberapp.screen.shared

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.husaynhakeem.barberapp.ui.theme.Green

@Preview(showBackground = true)
@Composable
fun Preview_ActionButton() {
    ActionButton(
        actionColor = Green,
        action = {},
    )
}