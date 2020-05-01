package com.husaynhakeem.fragmentresultsample.fragment_to_fragment

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.husaynhakeem.fragmentresultsample.R
import com.husaynhakeem.fragmentresultsample.context
import org.junit.Test

class ResultListenerFragmentTest {

    @Test
    fun shouldDisplayNumber() {
        val scenario = FragmentScenario.launchInContainer(ResultListenerFragment::class.java)
        val number = 20

        // Pass number from parent fragment manager
        scenario.onFragment { fragment ->
            val result = bundleOf(ResultListenerFragment.KEY_NUMBER to number)
            fragment.parentFragmentManager.setFragmentResult(
                ResultListenerFragment.REQUEST_KEY,
                result
            )
        }

        // Verify number is received and displayed
        val expected = scenario.context().getString(R.string.result_listener_number_message, number)
        onView(withId(R.id.resultListenerNumberTextView)).check(matches(withText(expected)))
    }
}