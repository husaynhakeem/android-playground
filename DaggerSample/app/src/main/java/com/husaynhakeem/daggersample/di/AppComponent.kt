package com.husaynhakeem.daggersample.di

import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, FeatureModule::class])
@Singleton
interface AppComponent {

    fun featureComponent(): FeatureComponent
}