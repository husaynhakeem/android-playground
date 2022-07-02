package com.husaynhakeem.menuhostsample

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment

private const val INITIAL_USERNAME = "Husayn Hakeem"

/**
 * A [Fragment] that will create its own menu using a [Toolbar].
 */
class FragmentOwnedAppBarProfileFragment :
    Fragment(R.layout.fragment_profile_fragment_owned_appbar) {

    private lateinit var toolbar: Toolbar
    private lateinit var usernameEditText: EditText

    private var savedUsername: String = INITIAL_USERNAME
    private var username: String = savedUsername

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.hide()
    }

    override fun onDestroy() {
        super.onDestroy()
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the toolbar
        toolbar = view.findViewById(R.id.toolbar)
        toolbar.inflateMenu(R.menu.menu_profile)
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.saveProfile -> {
                    saveProfile()
                    maybeUpdateMenu()
                    true
                }
                else -> false
            }
        }
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        maybeUpdateMenu()

        // Set up the username edit text
        usernameEditText = view.findViewById(R.id.profileUsernameEditText)
        usernameEditText.setText(username)
        usernameEditText.doOnTextChanged { text, _, _, _ ->
            username = text.toString()
            maybeUpdateMenu()
        }
    }

    private fun saveProfile() {
        // Store the new username
        savedUsername = username

        // Update the UI
        requireActivity().hideSoftKeyBoard()
        usernameEditText.clearFocus()

        Toast.makeText(requireContext(), R.string.profile_save_message, Toast.LENGTH_SHORT).show()
    }

    private fun maybeUpdateMenu() {
        val saveProfileItem = toolbar.menu.findItem(R.id.saveProfile)
        saveProfileItem.isEnabled = didUsernameChange()
    }

    private fun didUsernameChange(): Boolean {
        return savedUsername != username
    }
}