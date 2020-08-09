package com.husaynhakeem.daggersample.di

import com.husaynhakeem.daggersample.view.impl.AllNewsFragment
import com.husaynhakeem.daggersample.view.impl.NewsItemFragment
import dagger.Subcomponent

@FeatureScope
@Subcomponent(modules = [FeatureModule::class, StatsModule::class, ThirdPartyStatsModule::class])
interface FeatureComponent {

    fun inject(allNewsFragment: AllNewsFragment)
    fun inject(newsItemFragment: NewsItemFragment)
}