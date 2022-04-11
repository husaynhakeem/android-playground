package com.husaynhakeem.activityembeddingsample.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.husaynhakeem.activityembeddingsample.databinding.ActivityDetailsPlaceholderBinding

class DetailsPlaceholderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityDetailsPlaceholderBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}