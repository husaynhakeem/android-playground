package com.husaynhakeem.daggersample.repository

import com.husaynhakeem.daggersample.model.News

interface NewsRepository {

    fun all(): List<News>

    fun findBy(id: Long): News?
}