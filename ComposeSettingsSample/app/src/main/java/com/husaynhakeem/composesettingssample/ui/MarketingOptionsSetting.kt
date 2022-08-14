package com.husaynhakeem.composesettingssample.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.husaynhakeem.composesettingssample.MarketingOption
import com.husaynhakeem.composesettingssample.R


@Composable
fun MarketingOptionsSetting(
    modifier: Modifier = Modifier,
    title: String,
    selectedOption: MarketingOption,
    onOptionSelected: (option: MarketingOption) -> Unit,
) {
    val options = stringArrayResource(id = R.array.setting_marketing_options_choice)

    SettingItem(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(text = title)
            Spacer(modifier = Modifier.height(8.dp))
            options.forEachIndexed { index, option ->
                Row(
                    modifier = Modifier
                        .testTag(Tags.TAG_MARKETING_OPTION + index)
                        .fillMaxWidth()
                        .selectable(
                            selected = index == selectedOption.id,
                            role = Role.RadioButton
                        ) {
                            onOptionSelected(marketingOptionFrom(index))
                        }
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RadioButton(
                        selected = index == selectedOption.id,
                        onClick = null,
                    )
                    Text(
                        text = option,
                        modifier = Modifier.padding(start = 18.dp)
                    )
                }
            }
        }
    }
}

private fun marketingOptionFrom(id: Int): MarketingOption {
    return when (id) {
        MarketingOption.ALLOWED.id -> MarketingOption.ALLOWED
        MarketingOption.NOT_ALLOWED.id -> MarketingOption.NOT_ALLOWED
        else -> throw IllegalArgumentException("Unknown marketing option id $id")
    }
}

@Preview
@Composable
fun Preview_MarketingOptionsSetting() {
    MarketingOptionsSetting(
        title = stringResource(id = R.string.setting_marketing_options),
        selectedOption = MarketingOption.ALLOWED,
        onOptionSelected = {},
    )
}
