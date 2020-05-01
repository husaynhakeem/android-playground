package com.husaynhakeem.fragmentresultsample.fragment_to_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.util.Preconditions
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.setFragmentResultListener
import com.husaynhakeem.fragmentresultsample.R
import kotlinx.android.synthetic.main.fragment_result_listener.*

class ResultListenerFragment : Fragment(R.layout.fragment_result_listener) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpResultListener()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpEnterNumberButtonClickListener()
    }

    private fun setUpEnterNumberButtonClickListener() {
        resultListenerEnterNumberButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, ResultFragment::class.java, null)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setUpResultListener() {
        parentFragmentManager.setFragmentResultListener(
            REQUEST_KEY,
            this,
            FragmentResultListener { requestKey, result ->
                onFragmentResult(requestKey, result)
            })
    }

    private fun setUpResultListenerWithFMExtension() {
        setFragmentResultListener(REQUEST_KEY) { requestKey, result ->
            onFragmentResult(requestKey, result)
        }
    }

    @SuppressLint("RestrictedApi")
    private fun onFragmentResult(requestKey: String, result: Bundle) {
        Preconditions.checkState(REQUEST_KEY == requestKey)

        val number = result.getInt(KEY_NUMBER)
        resultListenerNumberTextView.text =
            getString(R.string.result_listener_number_message, number)
    }

    companion object {
        const val REQUEST_KEY = "result-listener-request-key"
        const val KEY_NUMBER = "key-number"
    }
}