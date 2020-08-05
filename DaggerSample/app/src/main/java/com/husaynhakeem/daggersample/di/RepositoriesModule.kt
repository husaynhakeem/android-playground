package com.husaynhakeem.daggersample.di

import com.husaynhakeem.daggersample.repository.NewsRepository
import com.husaynhakeem.daggersample.repository.impl.InMemoryNewsRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepositoriesModule {

    @Binds
    @Singleton
    fun provideNewsRepository(inMemoryRepository: InMemoryNewsRepository): NewsRepository
}