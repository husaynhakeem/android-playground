package com.husaynhakeem.fragmentresultsample.child_to_parent

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.util.Preconditions
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import com.husaynhakeem.fragmentresultsample.R
import kotlinx.android.synthetic.main.fragment_parent.*

class ParentFragment : Fragment(R.layout.fragment_parent) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpResultListener()
    }

    private fun setUpResultListener() {
        childFragmentManager.setFragmentResultListener(
            REQUEST_KEY,
            this,
            FragmentResultListener { requestKey, result ->
                onFragmentResult(requestKey, result)
            })
    }

    @SuppressLint("RestrictedApi")
    private fun onFragmentResult(requestKey: String, result: Bundle) {
        Preconditions.checkState(REQUEST_KEY == requestKey)

        val number = result.getInt(KEY_NUMBER)
        parentNumberTextView.text = getString(R.string.result_listener_number_message, number)
    }

    companion object {
        const val REQUEST_KEY = "parent-request-key"
        const val KEY_NUMBER = "key-number"
    }
}