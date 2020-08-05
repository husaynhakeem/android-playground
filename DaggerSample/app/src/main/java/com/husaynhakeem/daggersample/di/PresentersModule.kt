package com.husaynhakeem.daggersample.di

import com.husaynhakeem.daggersample.presenter.AllNewsPresenter
import com.husaynhakeem.daggersample.presenter.NewsItemPresenter
import com.husaynhakeem.daggersample.presenter.impl.AllNewsPresenterImpl
import com.husaynhakeem.daggersample.presenter.impl.NewsItemPresenterImpl
import com.husaynhakeem.daggersample.repository.NewsRepository
import dagger.Module
import dagger.Provides

@Module
object PresentersModule {

    @Provides
    fun provideAllNewsPresenter(repo: NewsRepository): AllNewsPresenter {
        return AllNewsPresenterImpl(repo)
    }

    @Provides
    fun provideNewsItemPresenter(repo: NewsRepository): NewsItemPresenter {
        return NewsItemPresenterImpl(repo)
    }
}