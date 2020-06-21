package com.husaynhakeem.hiltsample.data

interface PostsDataSource {

    suspend fun getPosts(): List<Post>
}