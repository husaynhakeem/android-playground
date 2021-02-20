package com.husaynhakeem.datastoresample.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.husaynhakeem.datastoresample.R
import com.husaynhakeem.datastoresample.databinding.FragmentHomeBinding
import com.husaynhakeem.datastoresample.login.LoginFragment

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up viewModel
        val factory = HomeViewModel.Factory(requireContext())
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.user.observe(viewLifecycleOwner, { user ->
            if (user == null) {
                Log.d("HomeFragment", "User instance is null")
                return@observe
            }
            binding.greeting.text = getString(R.string.label_greeting, user.id, user.token)
        })

        viewModel.openLoginScreen.observe(viewLifecycleOwner, { openLoginScreen ->
            if (openLoginScreen == true) {
                openLoginScreen()
            }
        })

        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }
    }

    private fun openLoginScreen() {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, LoginFragment())
            .commitNow()
    }
}