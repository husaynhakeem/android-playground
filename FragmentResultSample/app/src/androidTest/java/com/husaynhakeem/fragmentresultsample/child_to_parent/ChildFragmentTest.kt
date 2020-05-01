package com.husaynhakeem.fragmentresultsample.child_to_parent

import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.google.common.truth.Truth.assertThat
import com.husaynhakeem.fragmentresultsample.R
import org.junit.Test

class ChildFragmentTest {

    @Test
    fun shouldSendNumberToParent() {
        val scenario = FragmentScenario.launchInContainer(ChildFragment::class.java)
        val number = 20

        // Register result listener
        var receivedNumber = 0
        scenario.onFragment { fragment ->
            fragment.parentFragmentManager.setFragmentResultListener(
                ParentFragment.REQUEST_KEY,
                fragment,
                FragmentResultListener { _, result ->
                    receivedNumber = result.getInt(ParentFragment.KEY_NUMBER)
                })
        }

        // Send number
        onView(withId(R.id.childNumberEditText)).perform(
            typeText(number.toString()),
            closeSoftKeyboard()
        )
        onView(withId(R.id.childDoneButton)).perform(click())

        // Verify number is sent
        assertThat(receivedNumber).isEqualTo(number)
    }
}