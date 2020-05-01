package com.husaynhakeem.fragmentresultsample

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.FragmentScenario


fun <F : Fragment> FragmentScenario<F>.context(): Context {
    var context: Context? = null
    onFragment { fragment ->
        context = fragment.requireContext()
    }
    if (context == null) {
        throw IllegalStateException("Cannot get context from FragmentScenario")
    }
    return context!!
}