package com.husaynhakeem.datastoresample

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.husaynhakeem.datastoresample.databinding.ActivityMainBinding
import com.husaynhakeem.datastoresample.home.HomeViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModel()
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
        menu?.findItem(R.id.dayNightMode)?.icon = ContextCompat.getDrawable(this, icon)
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
        val factory = HomeViewModel.Factory(this)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        // Observe day/night mode changes
        viewModel.isNightModeEnabled.observe(this, { isNightModeEnabled ->
            val mode = if (isNightModeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(mode)
        })
    }
}