package com.husaynhakeem.composesettingssample.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class HintsSettingTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun title_isDisplayed() {
        val title = "Show hints"
        composeTestRule.setContent {
            HintsSetting(
                title = title,
                checked = false,
                onShowHintsToggled = {}
            )
        }
        composeTestRule
            .onNodeWithText(title)
            .assertIsDisplayed()
    }

    @Test
    fun setting_isChecked() {
        composeTestRule.setContent {
            HintsSetting(
                title = "Show hints",
                checked = true,
                onShowHintsToggled = {}
            )
        }
        composeTestRule
            .onNodeWithTag(Tags.TAG_CHECK_ITEM)
            .assertIsOn()
    }

    @Test
    fun setting_isNotChecked() {
        composeTestRule.setContent {
            HintsSetting(
                title = "Show hints",
                checked = false,
                onShowHintsToggled = {}
            )
        }
        composeTestRule
            .onNodeWithTag(Tags.TAG_CHECK_ITEM)
            .assertIsOff()
    }
}