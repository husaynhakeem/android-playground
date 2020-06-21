package com.husaynhakeem.hiltsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.husaynhakeem.hiltsample.ui.PostsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, PostsFragment::class.java, null)
                .commit()
        }
    }
}