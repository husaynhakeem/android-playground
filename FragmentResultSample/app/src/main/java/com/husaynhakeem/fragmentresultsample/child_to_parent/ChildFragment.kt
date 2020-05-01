package com.husaynhakeem.fragmentresultsample.child_to_parent

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.husaynhakeem.fragmentresultsample.R
import kotlinx.android.synthetic.main.fragment_child.*

class ChildFragment : Fragment(R.layout.fragment_child) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpDoneButtonClickListener()
    }

    private fun setUpDoneButtonClickListener() {
        childDoneButton.setOnClickListener {
            try {
                val number = childNumberEditText.text.toString().toInt()
                setResultWithFMExtension(number)
                childNumberEditText.setText("")
            } catch (exception: NumberFormatException) {
                Toast.makeText(
                    requireContext(),
                    R.string.invalid_number_error,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setResult(number: Int) {
        parentFragmentManager.setFragmentResult(
            ParentFragment.REQUEST_KEY,
            bundleOf(ParentFragment.KEY_NUMBER to number)
        )
    }

    private fun setResultWithFMExtension(number: Int) {
        setFragmentResult(
            ParentFragment.REQUEST_KEY,
            bundleOf(ParentFragment.KEY_NUMBER to number)
        )
    }
}