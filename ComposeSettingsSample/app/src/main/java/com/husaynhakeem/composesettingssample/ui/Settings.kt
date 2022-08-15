package com.husaynhakeem.composesettingssample.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.husaynhakeem.composesettingssample.SettingsViewModel


@Composable
fun Settings() {
    val viewModel: SettingsViewModel = viewModel()
    MaterialTheme {
        val state = viewModel.uiState.collectAsState()
        SettingsList(
            modifier = Modifier,
            state = state.value,
            viewModel = viewModel
        )
    }
}
