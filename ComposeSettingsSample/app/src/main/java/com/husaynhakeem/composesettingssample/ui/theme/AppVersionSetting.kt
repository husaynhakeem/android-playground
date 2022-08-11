package com.husaynhakeem.composesettingssample.ui.theme

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.husaynhakeem.composesettingssample.R
import com.husaynhakeem.composesettingssample.ui.SettingItem


@Composable
fun AppVersionSetting(
    modifier: Modifier = Modifier,
    title: String,
    appVersion: String,
) {
    SettingItem(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .semantics(mergeDescendants = true) {},
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = title, modifier = Modifier.weight(1f))
            Text(text = appVersion)
        }
    }
}

@Preview
@Composable
fun Preview_AppVersionSetting() {
    AppVersionSetting(
        title = stringResource(id = R.string.setting_app_version),
        appVersion = "2.1.0"
    )
}
