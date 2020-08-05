package com.husaynhakeem.daggersample.view

import com.husaynhakeem.daggersample.base.View
import com.husaynhakeem.daggersample.model.News

interface NewsItemView : View {

    fun displayNewsItem(news: News)

    fun onNewsItemNotFound(id: Long)
}