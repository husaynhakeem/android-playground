package com.husaynhakeem.menuhostsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.activityOwnedAppBar -> {
                findNavController(R.id.navHostFragment).navigate(R.id.activityOwnedAppBarProfileFragment)
                true
            }
            R.id.fragmentOwnedAppBar -> {
                findNavController(R.id.navHostFragment).navigate(R.id.fragmentOwnedAppBarProfileFragment)
                true
            }
            R.id.menuHost -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}