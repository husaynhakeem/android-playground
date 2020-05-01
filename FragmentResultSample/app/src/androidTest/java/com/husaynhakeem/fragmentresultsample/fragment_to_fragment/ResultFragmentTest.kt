package com.husaynhakeem.fragmentresultsample.fragment_to_fragment

import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.google.common.truth.Truth.assertThat
import com.husaynhakeem.fragmentresultsample.R
import org.junit.Test

class ResultFragmentTest {

    @Test
    fun shouldSendNumber() {
        val scenario = FragmentScenario.launchInContainer(ResultFragment::class.java)
        val number = 20

        // Register result listener
        var receivedNumber = 0
        scenario.onFragment { fragment ->
            fragment.parentFragmentManager.setFragmentResultListener(
                ResultListenerFragment.REQUEST_KEY,
                fragment,
                FragmentResultListener { _, result ->
                    receivedNumber = result.getInt(ResultListenerFragment.KEY_NUMBER)
                })
        }

        // Send number
        onView(withId(R.id.resultNumberEditText)).perform(
            typeText(number.toString()),
            closeSoftKeyboard()
        )
        onView(withId(R.id.resultDoneButton)).perform(click())

        // Verify number is sent
        assertThat(receivedNumber).isEqualTo(number)
    }
}