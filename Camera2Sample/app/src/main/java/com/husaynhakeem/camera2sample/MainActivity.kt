package com.husaynhakeem.camera2sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.husaynhakeem.camera2sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}