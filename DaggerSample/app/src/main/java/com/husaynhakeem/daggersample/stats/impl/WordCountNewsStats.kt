package com.husaynhakeem.daggersample.stats.impl

import android.util.Log
import com.husaynhakeem.daggersample.model.News
import com.husaynhakeem.daggersample.stats.NewsStats
import com.husaynhakeem.daggersample.stats.STATS_LOG

class WordCountNewsStats : NewsStats {

    override fun printStats(news: News) {
        val wordsCount = news.text.splitToSequence(" ").count()
        Log.i(STATS_LOG, "News Word count: $wordsCount")
    }
}