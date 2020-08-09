package com.husaynhakeem.daggersample.stats

import com.husaynhakeem.daggersample.model.News

const val STATS_LOG = "news-stats"

interface NewsStats {

    fun printStats(news: News)
}