package com.husaynhakeem.daggersample.presenter.impl

import com.husaynhakeem.daggersample.di.FeatureScope
import com.husaynhakeem.daggersample.presenter.AllNewsPresenter
import com.husaynhakeem.daggersample.repository.NewsRepository
import javax.inject.Inject

@FeatureScope
class AllNewsPresenterImpl @Inject constructor(private val repository: NewsRepository) :
    AllNewsPresenter() {

    override fun getAllNews() {
        val allNews = repository.all()
        view?.displayAllNews(allNews)
    }
}