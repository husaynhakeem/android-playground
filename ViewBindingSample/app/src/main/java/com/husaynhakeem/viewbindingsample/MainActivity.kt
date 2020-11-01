package com.husaynhakeem.viewbindingsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.husaynhakeem.viewbindingsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up instance of the binding class to use within this Activity.
        val binding = ActivityMainBinding.inflate(layoutInflater)

        // Set the root view of the binding as the active view on the screen.
        setContentView(binding.root)

        // Set OnClickListeners on the buttons
        binding.bindFragmentButton.setOnClickListener {
            switchFragment(FragmentBind())
        }
        binding.inflateFragmentButton.setOnClickListener {
            switchFragment(FragmentInflate())
        }

        // Initially show Bind fragment
        if (savedInstanceState == null) {
            setFragment(FragmentBind())
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.content, fragment)
            .commit()
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content, fragment)
            .commit()
    }
}
