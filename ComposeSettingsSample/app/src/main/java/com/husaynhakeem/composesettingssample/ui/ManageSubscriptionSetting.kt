package com.husaynhakeem.composesettingssample.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.husaynhakeem.composesettingssample.R


@Composable
fun ManageSubscriptionSetting(
    modifier: Modifier = Modifier,
    title: String,
    onSettingClicked: () -> Unit,
) {
    SettingItem(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .clickable(onClickLabel = stringResource(id = R.string.cd_open_subscription)) {
                    onSettingClicked()
                }
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                modifier = Modifier.weight(1f),
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
            )
        }
    }
}

@Preview
@Composable
fun Preview_ManageSubscriptionSetting() {
    ManageSubscriptionSetting(
        title = "Manage subscription",
        onSettingClicked = {},
    )
}
