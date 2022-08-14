package com.husaynhakeem.composesettingssample.ui

import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.husaynhakeem.composesettingssample.MarketingOption
import org.junit.Rule
import org.junit.Test

class MarketingOptionsSettingTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun marketingOption_isSelected() {
        val option = MarketingOption.NOT_ALLOWED
        composeTestRule.setContent {
            MarketingOptionsSetting(
                title = "Receive marketing emails?",
                selectedOption = option,
                onOptionSelected = {}
            )
        }
        composeTestRule
            .onNodeWithTag(Tags.TAG_MARKETING_OPTION + option.id)
            .assertIsSelected()
    }
}