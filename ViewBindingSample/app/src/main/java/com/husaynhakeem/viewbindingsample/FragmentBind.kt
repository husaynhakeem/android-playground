package com.husaynhakeem.viewbindingsample

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.husaynhakeem.viewbindingsample.databinding.FragmentBindBinding

class FragmentBind : Fragment(R.layout.fragment_bind) {

    // Instance of the view binding. Should only be accessed while the view is valid, i.e. between
    // onViewCreated and onDestroyView.
    private var _binding: FragmentBindBinding? = null
    private val binding: FragmentBindBinding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // The layout has already been inflated. Get an instance of the binding class using the
        // static [bind] method.
        _binding = FragmentBindBinding.bind(view)

        // Get access to the title TextView, available in all layouts.
        binding.title.text = getString(R.string.bind_fragment_title)

        // [subtitle] is only available in landscape, and should be null in portrait orientation.
        binding.subtitle?.text = getString(R.string.bind_fragment_subtitle_landscape)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Set the binding to null, since it should no longer be accessed after the view has been
        // destroyed.
        _binding = null
    }
}