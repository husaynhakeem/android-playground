package com.husaynhakeem.camera2sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.husaynhakeem.camera2sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)
    }
}