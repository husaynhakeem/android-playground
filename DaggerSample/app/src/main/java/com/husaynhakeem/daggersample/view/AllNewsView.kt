package com.husaynhakeem.daggersample.view

import com.husaynhakeem.daggersample.base.View
import com.husaynhakeem.daggersample.model.News

interface AllNewsView : View {

    fun displayAllNews(news: List<News>)
}