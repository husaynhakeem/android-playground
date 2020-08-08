package com.husaynhakeem.daggersample.di

import com.husaynhakeem.daggersample.repository.NewsRepository
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    fun newsRepository(): NewsRepository
}