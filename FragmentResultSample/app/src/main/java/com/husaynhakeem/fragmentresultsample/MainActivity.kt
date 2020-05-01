package com.husaynhakeem.fragmentresultsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.husaynhakeem.fragmentresultsample.fragment_to_fragment.ResultListenerFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            startFragmentToFragment()
        }
    }

    private fun startFragmentToFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, ResultListenerFragment::class.java, null)
            .commit()
    }
}