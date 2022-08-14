package com.husaynhakeem.composesettingssample.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class ManageSubscriptionSettingTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun title_isDisplayed() {
        val title = "Manage subscription"
        composeTestRule.setContent {
            ManageSubscriptionSetting(
                title = title,
                onSettingClicked = {}
            )
        }
        composeTestRule
            .onNodeWithText(title)
            .assertIsDisplayed()
    }

    @Test
    fun callback_isInvoked() {
        val title = "Manage subscription"
        val onSettingClicked: () -> Unit = mock()
        composeTestRule.setContent {
            ManageSubscriptionSetting(
                title = title,
                onSettingClicked = onSettingClicked,
            )
        }
        composeTestRule
            .onNodeWithText(title)
            .performClick()
        verify(onSettingClicked, times(1)).invoke()
    }
}