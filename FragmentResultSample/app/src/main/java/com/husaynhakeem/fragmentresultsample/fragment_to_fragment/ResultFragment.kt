package com.husaynhakeem.fragmentresultsample.fragment_to_fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.husaynhakeem.fragmentresultsample.R
import kotlinx.android.synthetic.main.fragment_result.*

class ResultFragment : Fragment(R.layout.fragment_result) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpResultDoneButtonClickListener()
    }

    private fun setUpResultDoneButtonClickListener() {
        resultDoneButton.setOnClickListener {
            try {
                val number = resultNumberEditText.text.toString().toInt()
                setResult(number)
                parentFragmentManager.popBackStack()
            } catch (exception: NumberFormatException) {
                Toast.makeText(
                    requireContext(),
                    R.string.result_invalid_number_error,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setResult(number: Int) {
        parentFragmentManager.setFragmentResult(
            ResultListenerFragment.REQUEST_KEY,
            bundleOf(ResultListenerFragment.KEY_NUMBER to number)
        )
    }

    private fun setResultWithFMExtension(number: Int) {
        setFragmentResult(
            ResultListenerFragment.REQUEST_KEY,
            bundleOf(ResultListenerFragment.KEY_NUMBER to number)
        )
    }
}
