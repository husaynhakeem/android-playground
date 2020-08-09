package com.husaynhakeem.daggersample.stats.impl

import android.util.Log
import com.husaynhakeem.daggersample.model.News
import com.husaynhakeem.daggersample.stats.NewsStats
import com.husaynhakeem.daggersample.stats.STATS_LOG

class LengthNewsStats : NewsStats {

    override fun printStats(news: News) {
        val length = news.text.length
        Log.i(STATS_LOG, "News Length: $length")
    }
}