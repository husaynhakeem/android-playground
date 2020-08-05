package com.husaynhakeem.daggersample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.husaynhakeem.daggersample.view.impl.AllNewsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.content, AllNewsFragment())
                .commit()
        }
    }
}