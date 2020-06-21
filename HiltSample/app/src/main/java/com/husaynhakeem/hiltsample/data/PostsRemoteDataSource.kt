package com.husaynhakeem.hiltsample.data

import com.husaynhakeem.hiltsample.network.PostsService
import javax.inject.Inject

class PostsRemoteDataSource @Inject constructor(private val postsService: PostsService) :
    PostsDataSource {

    override suspend fun getPosts(): List<Post> {
        return postsService.getPosts()
    }
}