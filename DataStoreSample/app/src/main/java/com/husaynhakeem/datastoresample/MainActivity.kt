package com.husaynhakeem.datastoresample

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViewModel()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.isNightModeEnabled.observe(this, Observer { isNightModeEnabled ->
            // Update night mode
            val mode = if (isNightModeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(mode)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.dayNightMode) {
            viewModel.toggleDayNightMode()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val icon = if (viewModel.isNightModeEnabled.value == true) {
            R.drawable.ic_dark_mode
        } else {
            R.drawable.ic_light_mode
        }
        menu?.findItem(R.id.dayNightMode)?.setIcon(ContextCompat.getDrawable(this, icon))
        return true
    }
}