package com.husaynhakeem.composesettingssample.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class NotificationsSettingTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun title_isDisplayed() {
        val title = "Enable notifications"
        composeTestRule.setContent {
            NotificationsSetting(
                title = title,
                checked = false,
                onCheckedChanged = {}
            )
        }
        composeTestRule
            .onNodeWithText(title)
            .assertIsDisplayed()
    }

    @Test
    fun setting_isChecked() {
        composeTestRule.setContent {
            NotificationsSetting(
                title = "Enable notifications",
                checked = true,
                onCheckedChanged = {}
            )
        }
        composeTestRule
            .onNodeWithTag(Tags.TAG_TOGGLE_ITEM)
            .assertIsOn()
    }

    @Test
    fun setting_isNotChecked() {
        composeTestRule.setContent {
            NotificationsSetting(
                title = "Enable notifications",
                checked = false,
                onCheckedChanged = {}
            )
        }
        composeTestRule
            .onNodeWithTag(Tags.TAG_TOGGLE_ITEM)
            .assertIsOff()
    }
}