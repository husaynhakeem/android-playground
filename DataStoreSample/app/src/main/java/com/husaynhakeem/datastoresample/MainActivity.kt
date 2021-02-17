package com.husaynhakeem.datastoresample

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.husaynhakeem.datastoresample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModel()
        setUpSaveData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val icon = if (viewModel.isNightModeEnabled.value == true) {
            R.drawable.ic_light_mode
        } else {
            R.drawable.ic_dark_mode
        }
        menu?.findItem(R.id.dayNightMode)?.setIcon(ContextCompat.getDrawable(this, icon))
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.dayNightMode) {
            viewModel.toggleDayNightMode()
            return true
        }
        return false
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        ).get(MainViewModel::class.java)

        // Observe day/night mode changes
        viewModel.isNightModeEnabled.observe(this, { isNightModeEnabled ->
            val mode = if (isNightModeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(mode)
        })

        // Observe person information
        viewModel.person.observe(this, { person ->
            if (person == null) {
                return@observe
            }

            binding.firstNameEditText.setText(person.firstName)
            binding.lastNameEditText.setText(person.lastName)
            binding.addressStreetEditText.setText(person.address.street)
            binding.addressCityEditText.setText(person.address.city)
            binding.addressZipCodeEditText.setText(person.address.zipCode.toString())
            binding.addressCountryEditText.setText(person.address.country)
            binding.phoneCountryCodeEditText.setText(person.phoneNumber.countryCode.toString())
            binding.phoneNumberEditText.setText(person.phoneNumber.number.toString())
        })
    }

    private fun setUpSaveData() {
        binding.saveButton.setOnClickListener {
            try {
                val address = Address(
                    street = binding.addressStreetEditText.content(),
                    city = binding.addressCityEditText.content(),
                    country = binding.addressCountryEditText.content(),
                    zipCode = binding.addressZipCodeEditText.content().toInt()
                )
                val phoneNumber = PhoneNumber(
                    countryCode = binding.phoneCountryCodeEditText.content().toInt(),
                    number = binding.phoneNumberEditText.content().toInt()
                )
                val person = Person(
                    firstName = binding.firstNameEditText.content(),
                    lastName = binding.lastNameEditText.content(),
                    address = address,
                    phoneNumber = phoneNumber
                )
                viewModel.savePerson(person)
            } catch (exception: Exception) {
                Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()
                exception.printStackTrace()
            }
        }
    }

    private fun EditText.content(): String {
        return text.toString()
    }
}