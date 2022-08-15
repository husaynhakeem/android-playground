package com.husaynhakeem.composesettingssample.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class AppVersionSettingTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun appVersion_isDisplayed() {
        val version = "1.0.4"
        composeTestRule.setContent {
            AppVersionSetting(
                title = "App version",
                appVersion = version
            )
        }
        composeTestRule
            .onNodeWithText(version)
            .assertIsDisplayed()
    }
}