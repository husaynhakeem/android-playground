package com.husaynhakeem.composesettingssample.ui

import androidx.annotation.StringRes
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.husaynhakeem.composesettingssample.Theme
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class ThemeSettingTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun selectedTheme_isDisplayed() {
        val theme = Theme.DARK
        composeTestRule.setContent {
            ThemeSetting(
                title = "Theme",
                selectedTheme = theme,
                onOptionSelected = {}
            )
        }
        composeTestRule
            .onNodeWithTag(Tags.TAG_THEME, useUnmergedTree = true)
            .assertTextEquals(
                InstrumentationRegistry
                    .getInstrumentation()
                    .targetContext
                    .getString(theme.label)
            )
    }

    @Test
    fun allThemes_areDisplayed() {
        composeTestRule.setContent {
            ThemeSetting(
                title = "Theme",
                selectedTheme = Theme.LIGHT,
                onOptionSelected = {}
            )
        }
        composeTestRule
            .onNodeWithTag(Tags.TAG_SELECT_THEME)
            .performClick()
        Theme.values()
            .forEach { theme ->
                composeTestRule
                    .onNodeWithTag(Tags.TAG_THEME_OPTION + stringResource(theme.label))
                    .assertIsDisplayed()
            }
    }

    @Test
    fun callback_inInvoked() {
        val onOptionSelected: (Theme) -> Unit = mock()
        composeTestRule.setContent {
            ThemeSetting(
                title = "Theme",
                selectedTheme = Theme.LIGHT,
                onOptionSelected = onOptionSelected
            )
        }
        composeTestRule
            .onNodeWithTag(Tags.TAG_SELECT_THEME)
            .performClick()
        composeTestRule
            .onNodeWithTag(Tags.TAG_THEME_OPTION + stringResource(Theme.DARK.label))
            .performClick()
        verify(onOptionSelected, times(1)).invoke(Theme.DARK)
    }

    private fun stringResource(@StringRes id: Int) = InstrumentationRegistry.getInstrumentation()
        .targetContext
        .getString(id)
}