package com.husaynhakeem.composesettingssample

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class SettingsViewModel : ViewModel() {

    val uiState = MutableStateFlow(SettingsState())

    fun toggleNotificationsSetting() {
        uiState.value =
            uiState.value.copy(notificationsEnabled = !uiState.value.notificationsEnabled)
    }

    fun toggleHintsSetting() {
        uiState.value = uiState.value.copy(hintsEnabled = !uiState.value.hintsEnabled)
    }

    fun setMarketingSetting(option: MarketingOption) {
        uiState.value = uiState.value.copy(marketingOption = option)
    }

    fun setThemeSetting(theme: Theme) {
        uiState.value = uiState.value.copy(themeOption = theme)
    }
}