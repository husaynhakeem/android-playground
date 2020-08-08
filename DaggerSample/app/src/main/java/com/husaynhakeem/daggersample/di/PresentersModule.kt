package com.husaynhakeem.daggersample.di

import com.husaynhakeem.daggersample.presenter.AllNewsPresenter
import com.husaynhakeem.daggersample.presenter.NewsItemPresenter
import com.husaynhakeem.daggersample.presenter.impl.AllNewsPresenterImpl
import com.husaynhakeem.daggersample.presenter.impl.NewsItemPresenterImpl
import dagger.Binds
import dagger.Module

@Module
interface PresentersModule {

    @Binds
    fun provideAllNewsPresenter(allNewsPresenterImpl: AllNewsPresenterImpl): AllNewsPresenter

    @Binds
    fun provideNewsItemPresenter(newsItemPresenterImpl: NewsItemPresenterImpl): NewsItemPresenter
}