package com.husaynhakeem.viewbindingsample

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun showBindFragment_whenBindFragmentButtonClicked() {
        // Act
        onView(withId(R.id.bindFragmentButton)).perform(click())

        // Assert
        onView(withId(R.id.bindFragmentRoot)).check(matches(isDisplayed()))
    }

    @Test
    fun showInflateFragment_whenInflateFragmentButtonClicked() {
        // Act
        onView(withId(R.id.inflateFragmentButton)).perform(click())

        // Assert
        onView(withId(R.id.inflateFragmentRoot)).check(matches(isDisplayed()))
    }
}