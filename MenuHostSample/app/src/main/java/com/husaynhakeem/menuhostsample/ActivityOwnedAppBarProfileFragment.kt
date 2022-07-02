package com.husaynhakeem.menuhostsample

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment

private const val INITIAL_USERNAME = "Husayn Hakeem"

/**
 * A [Fragment] that will inflate a menu and add it to its host [Activity]'s app bar menu.
 */
class ActivityOwnedAppBarProfileFragment :
    Fragment(R.layout.fragment_profile_activity_owned_appbar) {

    private lateinit var usernameEditText: EditText

    private var savedUsername: String = INITIAL_USERNAME
    private var username: String = savedUsername

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as? AppCompatActivity)?.supportActionBar?.setTitle(R.string.activity_owned_appbar_title)

        usernameEditText = view.findViewById(R.id.profileUsernameEditText)
        usernameEditText.setText(username)
        usernameEditText.doOnTextChanged { text, _, _, _ ->
            username = text.toString()
            maybeUpdateMenu()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_profile, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.saveProfile -> {
                saveProfile()
                maybeUpdateMenu()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val saveProfileItem = menu.findItem(R.id.saveProfile)
        saveProfileItem.isEnabled = didUsernameChange()
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
        requireActivity().invalidateOptionsMenu()
    }

    private fun didUsernameChange(): Boolean {
        return savedUsername != username
    }
}