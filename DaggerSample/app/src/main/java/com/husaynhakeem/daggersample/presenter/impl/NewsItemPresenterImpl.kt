package com.husaynhakeem.daggersample.presenter.impl

import com.husaynhakeem.daggersample.di.FeatureScope
import com.husaynhakeem.daggersample.presenter.NewsItemPresenter
import com.husaynhakeem.daggersample.repository.NewsRepository
import com.husaynhakeem.daggersample.stats.NewsStats
import javax.inject.Inject

@FeatureScope
class NewsItemPresenterImpl @Inject constructor(
    private val repository: NewsRepository,
    private val newsStats: @JvmSuppressWildcards(true) Set<NewsStats>
) : NewsItemPresenter() {

    override fun getNewsItem(id: Long) {
        val news = repository.findBy(id)
        if (news == null) {
            view?.onNewsItemNotFound(id)
        } else {
            view?.displayNewsItem(news)
            newsStats.forEach { it.printStats(news) }
        }
    }
}