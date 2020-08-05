package com.husaynhakeem.daggersample.repository.impl

import com.husaynhakeem.daggersample.model.News
import com.husaynhakeem.daggersample.repository.NewsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InMemoryNewsRepository @Inject constructor() : NewsRepository {

    private val newsCollection = mutableMapOf<Long, News>()

    init {
        for (n in 1..100) {
            val news = News(
                id = n.toLong(),
                title = "Lorem Ipsum #$n",
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
                        "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam," +
                        " quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                        "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu " +
                        "fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in " +
                        "culpa qui officia deserunt mollit anim id est laborum. Sed ut perspiciatis unde omnis " +
                        "iste natus error sit voluptatem accusantium doloremque laudantium, totam rem " +
                        "aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae " +
                        "vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur " +
                        "aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem " +
                        "sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, " +
                        "consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut " +
                        "labore et dolore magnam aliquam quaerat voluptatem."
            )
            newsCollection[n.toLong()] = news
        }
    }

    override fun all() = newsCollection.values.toList()

    override fun findBy(id: Long) = newsCollection[id]
}