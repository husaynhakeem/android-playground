package com.husaynhakeem.hiltsample.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Qualifier
annotation class IoDispatcher

@Module
@InstallIn(ApplicationComponent::class)
object ConcurrencyModule {

    @Provides
    @IoDispatcher
    fun ioDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}