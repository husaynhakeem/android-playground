package com.husaynhakeem.activityembeddingsample.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.husaynhakeem.activityembeddingsample.databinding.ActivityDetailPlaceholderBinding

class DetailPlaceholderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityDetailPlaceholderBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}