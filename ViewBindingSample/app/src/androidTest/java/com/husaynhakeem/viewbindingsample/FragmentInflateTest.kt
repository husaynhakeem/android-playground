package com.husaynhakeem.viewbindingsample

import android.os.Build
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assume.assumeTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FragmentInflateTest {

    @Test
    fun doNotShowSubtitle_onApisBelow23() {
        // Arrange
        assumeTrue(Build.VERSION.SDK_INT < 23)
        launchFragmentInContainer { FragmentInflate() }

        // Assert
        onView(withId(R.id.subtitle)).check(doesNotExist())
    }

    @Test
    fun showSubtitle_onApis23AndAbove() {
        // Arrange
        assumeTrue(Build.VERSION.SDK_INT >= 23)
        launchFragmentInContainer { FragmentInflate() }

        // Assert
        onView(withId(R.id.subtitle)).check(matches(isDisplayed()))
    }
}