package com.husaynhakeem.composesettingssample.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.husaynhakeem.composesettingssample.R
import com.husaynhakeem.composesettingssample.Theme


@Composable
fun ThemeSetting(
    modifier: Modifier = Modifier,
    title: String,
    selectedTheme: Theme,
    onOptionSelected: (option: Theme) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    SettingItem(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .testTag(Tags.TAG_SELECT_THEME)
                .clickable(onClickLabel = stringResource(id = R.string.cd_select_theme)) {
                    expanded = !expanded
                }
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                modifier = Modifier.weight(1f),
            )
            Text(
                text = stringResource(id = selectedTheme.label),
                modifier = Modifier.testTag(Tags.TAG_THEME)
            )
        }
        DropdownMenu(
            expanded = expanded,
            offset = DpOffset(16.dp, 0.dp),
            onDismissRequest = { expanded = false },
        ) {
            Theme.values()
                .forEach { theme ->
                    DropdownMenuItem(
                        modifier = Modifier.testTag(Tags.TAG_THEME_OPTION + stringResource(id = theme.label)),
                        onClick = {
                            onOptionSelected(theme)
                            expanded = false
                        }) {
                        Text(text = stringResource(id = theme.label))
                    }
                }
        }
    }
}

@Preview
@Composable
fun Preview_ThemeSetting() {
    ThemeSetting(
        title = stringResource(id = R.string.setting_theme_option),
        selectedTheme = Theme.SYSTEM,
        onOptionSelected = {},
    )
}
