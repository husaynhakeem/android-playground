package com.husaynhakeem.hiltsample.network

import com.husaynhakeem.hiltsample.data.Post
import retrofit2.http.GET

interface PostsService {

    @GET("posts")
    suspend fun getPosts(): List<Post>
}