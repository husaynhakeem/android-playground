package com.husaynhakeem.daggersample.di

import com.husaynhakeem.daggersample.stats.NewsStats
import com.husaynhakeem.daggersample.stats.impl.WordCountNewsStats
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet

@Module
class ThirdPartyStatsModule {

    @Provides
    @IntoSet
    fun providesWordsLengthNewsStats(): NewsStats {
        return WordCountNewsStats()
    }
}