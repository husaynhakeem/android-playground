package com.husaynhakeem.viewbindingsample

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.husaynhakeem.viewbindingsample.databinding.FragmentInflateBinding

class FragmentInflate : Fragment() {

    // Instance of the view binding. Should only be accessed while the view is valid, i.e. between
    // onCreateView and onDestroyView.
    private var _binding: FragmentInflateBinding? = null
    private val binding: FragmentInflateBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Set up instance of the binding class to use with this fragment by using the static
        // [inflate] method.
        _binding = FragmentInflateBinding.inflate(inflater)

        // Return the root of the binding, which references the LinearLayout.
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get access to the title TextView, available in all layouts.
        binding.title.text = getString(R.string.inflate_fragment_title)

        // [subtitle] is only available in api levels 23 and above, and should be null in devices
        // running on older versions.
        val apiLevel = Build.VERSION.SDK_INT
        binding.subtitle?.text = getString(R.string.inflate_fragment_subtitle_pre_api_23, apiLevel)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Set the binding to null, since it should no longer be accessed after the view has been
        // destroyed.
        _binding = null
    }
}