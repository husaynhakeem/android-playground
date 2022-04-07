package com.husaynhakeem.activityembeddingsample

import android.app.Application
import androidx.window.core.ExperimentalWindowApi
import androidx.window.embedding.SplitController

class ActivityEmbeddingSampleApplication: Application() {

    @OptIn(ExperimentalWindowApi::class)
    override fun onCreate() {
        super.onCreate()
        SplitController.initialize(this, R.xml.split_configuration)
    }
}