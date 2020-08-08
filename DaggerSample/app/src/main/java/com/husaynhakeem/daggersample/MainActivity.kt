package com.husaynhakeem.daggersample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.husaynhakeem.daggersample.di.DaggerFeatureComponent
import com.husaynhakeem.daggersample.di.FeatureComponent
import com.husaynhakeem.daggersample.view.impl.AllNewsFragment
import javax.inject.Provider

class MainActivity : AppCompatActivity(), Provider<FeatureComponent> {

    private lateinit var featureComponent: FeatureComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Init feature component
        featureComponent = DaggerFeatureComponent.builder()
            .appComponent((applicationContext as DaggerApp).appComponent())
            .build()

        // Attach fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.content, AllNewsFragment())
                .commit()
        }
    }

    override fun get(): FeatureComponent = featureComponent
}