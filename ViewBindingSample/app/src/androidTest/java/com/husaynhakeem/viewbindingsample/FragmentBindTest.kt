package com.husaynhakeem.viewbindingsample

import android.content.pm.ActivityInfo
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FragmentBindTest {

    @Test
    fun doNotShowSubtitle_inPortraitOrientation() {
        // Arrange
        val scenario = launchFragmentInContainer { FragmentBind() }

        // Act
        scenario.onFragment { fragment ->
            fragment.requireActivity().requestedOrientation =
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        // Assert
        onView(withId(R.id.subtitle)).check(doesNotExist())
    }

    @Test
    fun showSubtitle_inLandscapeOrientation() {
        // Arrange
        val scenario = launchFragmentInContainer { FragmentBind() }

        // Act
        scenario.onFragment { fragment ->
            fragment.requireActivity().requestedOrientation =
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        // Assert
        onView(withId(R.id.subtitle)).check(matches(isDisplayed()))
    }
}