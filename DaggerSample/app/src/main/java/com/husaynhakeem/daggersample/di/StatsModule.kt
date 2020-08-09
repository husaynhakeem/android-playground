package com.husaynhakeem.daggersample.di

import com.husaynhakeem.daggersample.stats.NewsStats
import com.husaynhakeem.daggersample.stats.impl.LengthNewsStats
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet

@Module
class StatsModule {

    @Provides
    @IntoSet
    fun provideNewsStats(): NewsStats {
        return LengthNewsStats()
    }
}
