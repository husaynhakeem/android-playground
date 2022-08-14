package com.husaynhakeem.composesettingssample.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.husaynhakeem.composesettingssample.R


@Composable
fun HintsSetting(
    modifier: Modifier = Modifier,
    title: String,
    checked: Boolean,
    onShowHintsToggled: (checked: Boolean) -> Unit,
) {
    val hintsEnabledState =
        if (checked) stringResource(id = R.string.cd_hints_enabled) else stringResource(id = R.string.cd_hints_disabled)

    SettingItem(
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .testTag(Tags.TAG_CHECK_ITEM)
                .toggleable(
                    value = checked,
                    onValueChange = onShowHintsToggled,
                    role = Role.Checkbox
                )
                .semantics {
                    stateDescription = hintsEnabledState
                }
                .padding(horizontal = 16.dp),
        ) {
            Text(
                text = title,
                modifier = Modifier.weight(1f),
            )
            Checkbox(
                checked = checked,
                onCheckedChange = null,
            )
        }
    }
}

@Preview(name = "Hints shown")
@Composable
fun Preview_HintsSetting_Shown() {
    HintsSetting(
        title = "Show hints",
        checked = true,
        onShowHintsToggled = {},
    )
}

@Preview(name = "Hints hidden")
@Composable
fun Preview_HintsSetting_Hide() {
    HintsSetting(
        title = "Show hints",
        checked = false,
        onShowHintsToggled = {},
    )
}
