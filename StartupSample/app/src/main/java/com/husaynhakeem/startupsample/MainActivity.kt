package com.husaynhakeem.startupsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.startup.AppInitializer
import com.husaynhakeem.first_dependency.Initializer1A
import com.husaynhakeem.first_dependency.Initializer1B
import com.husaynhakeem.second_dependency.Initializer2A
import com.husaynhakeem.second_dependency.Initializer2B
import com.husaynhakeem.second_dependency.Initializer2C
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Automatic initialization
        if (isAutomaticInitializationDone()) {
            automaticInitialization.setText(R.string.automatic_initialization_done)
        } else {
            automaticInitialization.setText(R.string.automatic_initialization_not_done)
        }

        // Manual initialization
        if (isManualInitializationDone()) {
            manualInitialization.setText(R.string.manual_initialization_done)
        } else {
            manualInitialization.setText(R.string.manual_initialization_not_done)
        }

        // Perform manual initialization
        performManualInitialization.setOnClickListener {
            AppInitializer.getInstance(applicationContext)
                .initializeComponent(Initializer1B::class.java)


        }
    }

    private fun isAutomaticInitializationDone(): Boolean {
        return Initializer1A.Dependency.isInitialized && Initializer2A.Dependency.isInitialized
                && Initializer2B.Dependency.isInitialized && Initializer2C.Dependency.isInitialized
    }

    private fun isManualInitializationDone(): Boolean {
        return Initializer1B.Dependency.isInitialized
    }
}