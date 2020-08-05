package com.husaynhakeem.daggersample

import android.app.Application
import com.husaynhakeem.daggersample.di.AppComponent
import com.husaynhakeem.daggersample.di.DaggerAppComponent

class DaggerApp : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().build()
    }

    fun appComponent() = appComponent
}