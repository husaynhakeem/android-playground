package com.husaynhakeem.daggersample.presenter

import com.husaynhakeem.daggersample.base.Presenter
import com.husaynhakeem.daggersample.view.NewsItemView

abstract class NewsItemPresenter : Presenter<NewsItemView>() {

    abstract fun getNewsItem(id: Long)
}