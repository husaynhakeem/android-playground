package com.husaynhakeem.fragmentresultsample.child_to_parent

import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.google.common.truth.Truth.assertThat
import com.husaynhakeem.fragmentresultsample.R
import com.husaynhakeem.fragmentresultsample.context
import org.junit.Test

class ParentFragmentTest {

    @Test
    fun shouldDisplayNumber() {
        val scenario = FragmentScenario.launchInContainer(ParentFragment::class.java)
        val number = 20

        // Get child fragment
        var childFragment: Fragment
        scenario.onFragment { fragment ->
            childFragment = fragment.childFragmentManager.fragments.first()
            assertThat(childFragment).isInstanceOf(ChildFragment::class.java)
        }

        // Pass number from child to parent
        onView(withId(R.id.childNumberEditText)).perform(
            typeText(number.toString()),
            closeSoftKeyboard()
        )
        onView(withId(R.id.childDoneButton)).perform(click())

        // Verify number is received and displayed
        val expected = scenario.context().getString(R.string.result_listener_number_message, number)
        onView(withId(R.id.parentNumberTextView)).check(matches(withText(expected)))
    }
}