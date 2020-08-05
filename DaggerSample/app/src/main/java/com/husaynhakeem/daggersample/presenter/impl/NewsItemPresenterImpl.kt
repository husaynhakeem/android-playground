package com.husaynhakeem.daggersample.presenter.impl

import com.husaynhakeem.daggersample.presenter.NewsItemPresenter
import com.husaynhakeem.daggersample.repository.NewsRepository
import javax.inject.Inject

class NewsItemPresenterImpl @Inject constructor(private val repository: NewsRepository) :
    NewsItemPresenter() {

    override fun getNewsItem(id: Long) {
        val news = repository.findBy(id)
        if (news == null) {
            view?.onNewsItemNotFound(id)
        } else {
            view?.displayNewsItem(news)
        }
    }
}