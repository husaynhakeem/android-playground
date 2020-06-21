package com.husaynhakeem.hiltsample.data

import javax.inject.Inject

class PostsFakeDataSource @Inject constructor() : PostsDataSource {

    override suspend fun getPosts(): List<Post> {
        return (1..100).map { Post(it, it, "Title $it", "This is the body $it") }
    }
}