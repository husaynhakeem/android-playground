package com.husaynhakeem.composesettingssample.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.husaynhakeem.composesettingssample.R
import com.husaynhakeem.composesettingssample.SettingsState
import com.husaynhakeem.composesettingssample.SettingsViewModel

@Composable
fun SettingsList(
    modifier: Modifier = Modifier,
    state: SettingsState,
    viewModel: SettingsViewModel,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        TopAppBar(
            backgroundColor = MaterialTheme.colors.surface,
            contentPadding = PaddingValues(start = 12.dp)
        ) {
            Row {
                Icon(
                    tint = MaterialTheme.colors.onSurface,
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.cd_go_back)
                )
                Spacer(modifier = modifier.width(16.dp))
                Text(
                    text = stringResource(id = R.string.title_settings),
                    fontSize = 18.sp,
                    color = MaterialTheme.colors.onSurface,
                )
            }
        }

        NotificationsSetting(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(id = R.string.setting_enable_notifications),
            checked = state.notificationsEnabled,
            onCheckedChanged = { viewModel.toggleNotificationsSetting() }
        )

        Divider()

        HintsSetting(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(id = R.string.setting_show_hints),
            checked = state.hintsEnabled,
            onShowHintsToggled = { viewModel.toggleHintsSetting() }
        )

        Divider()

        ManageSubscriptionSetting(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(id = R.string.setting_manage_subscription),
            onSettingClicked = {}
        )

        SectionSpacer(modifier = Modifier.fillMaxWidth())

        MarketingOptionsSetting(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(id = R.string.setting_marketing_options),
            selectedOption = state.marketingOption,
            onOptionSelected = viewModel::setMarketingSetting
        )

        Divider()

        ThemeSetting(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(id = R.string.setting_theme_option),
            selectedTheme = state.themeOption,
            onOptionSelected = viewModel::setThemeSetting
        )

        SectionSpacer(modifier = Modifier.fillMaxWidth())

        AppVersionSetting(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(id = R.string.setting_app_version),
            appVersion = stringResource(id = R.string.app_version)
        )
    }
}

@Preview
@Composable
fun Preview_SettingsList() {
    SettingsList(
        state = SettingsState(),
        viewModel = SettingsViewModel()
    )
}