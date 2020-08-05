package com.husaynhakeem.daggersample.di

import com.husaynhakeem.daggersample.view.impl.AllNewsFragment
import com.husaynhakeem.daggersample.view.impl.NewsItemFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [PresentersModule::class, RepositoriesModule::class])
@Singleton
interface AppComponent {

    fun inject(allNewsFragment: AllNewsFragment)
    fun inject(newsItemFragment: NewsItemFragment)
}