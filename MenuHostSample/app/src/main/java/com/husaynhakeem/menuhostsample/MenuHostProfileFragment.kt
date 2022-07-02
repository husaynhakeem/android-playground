package com.husaynhakeem.menuhostsample

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment

private const val INITIAL_USERNAME = "Husayn Hakeem"

/**
 * A [Fragment] that will inject menu items into its host [Activity]'s menu using the [MenuHost] API.
 */
class MenuHostProfileFragment : Fragment(R.layout.fragment_profile_menu_host) {

    private lateinit var usernameEditText: EditText

    private var savedUsername: String = INITIAL_USERNAME
    private var username: String = savedUsername

    private val menuHost: MenuHost
        get() = requireActivity()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as? AppCompatActivity)?.supportActionBar?.setTitle(R.string.menu_host_title)

        // Set up menu host to inject menu items into the host activity's app bar menu.
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_profile, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.saveProfile -> {
                        saveProfile()
                        maybeUpdateMenu()
                        true
                    }
                    else -> false
                }
            }

            override fun onPrepareMenu(menu: Menu) {
                val saveProfileItem = menu.findItem(R.id.saveProfile)
                saveProfileItem.isEnabled = didUsernameChange()
            }
        }, viewLifecycleOwner)

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
        menuHost.invalidateMenu()
    }

    private fun didUsernameChange(): Boolean {
        return savedUsername != username
    }
}