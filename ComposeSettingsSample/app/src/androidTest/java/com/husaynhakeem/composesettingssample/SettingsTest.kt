package com.husaynhakeem.composesettingssample

import androidx.annotation.StringRes
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.husaynhakeem.composesettingssample.ui.Settings
import com.husaynhakeem.composesettingssample.ui.Tags
import org.junit.Rule
import org.junit.Test

class SettingsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // region Settings are displayed
    @Test
    fun enableNotificationsSetting_isDisplayed() {
        assertSettingIsDisplayed(R.string.setting_enable_notifications)
    }

    @Test
    fun showHintsSetting_isDisplayed() {
        assertSettingIsDisplayed(R.string.setting_show_hints)
    }

    @Test
    fun manageSubscriptionsSetting_isDisplayed() {
        assertSettingIsDisplayed(R.string.setting_manage_subscription)
    }

    @Test
    fun marketingOptionsSetting_isDisplayed() {
        assertSettingIsDisplayed(R.string.setting_marketing_options)
    }

    @Test
    fun themeSetting_isDisplayed() {
        assertSettingIsDisplayed(R.string.setting_theme_option)
    }

    @Test
    fun appVersionSetting_isDisplayed() {
        assertSettingIsDisplayed(R.string.setting_app_version)
    }

    private fun assertSettingIsDisplayed(@StringRes title: Int) {
        composeTestRule.setContent {
            Settings()
        }
        composeTestRule
            .onNodeWithText(stringResource(title))
            .assertIsDisplayed()
    }
    // endregion Settings are displayed

    // region Interactions with settings
    @Test
    fun enableNotifications_togglesSelectedState() {
        composeTestRule.setContent {
            Settings()
        }
        composeTestRule
            .onNodeWithText(stringResource(R.string.setting_enable_notifications))
            .performClick()
        composeTestRule
            .onNodeWithTag(Tags.TAG_TOGGLE_ITEM)
            .assertIsOn()
    }

    @Test
    fun showHints_togglesSelectedState() {
        composeTestRule.setContent {
            Settings()
        }
        composeTestRule
            .onNodeWithText(stringResource(R.string.setting_show_hints))
            .performClick()
        composeTestRule
            .onNodeWithTag(Tags.TAG_CHECK_ITEM)
            .assertIsOn()
    }

    @Test
    fun marketingOptions_togglesSelectedState() {
        composeTestRule.setContent {
            Settings()
        }
        composeTestRule
            .onNodeWithText(
                InstrumentationRegistry.getInstrumentation()
                    .targetContext
                    .resources
                    .getStringArray(R.array.setting_marketing_options_choice)[1]
            )
            .performClick()
        composeTestRule
            .onNodeWithTag(Tags.TAG_MARKETING_OPTION + 1)
            .assertIsSelected()
    }
    // endregion Interactions with settings

    private fun stringResource(@StringRes id: Int) = InstrumentationRegistry.getInstrumentation()
        .targetContext
        .getString(id)
}