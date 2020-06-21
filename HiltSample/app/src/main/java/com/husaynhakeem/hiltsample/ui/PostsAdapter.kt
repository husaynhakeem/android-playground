package com.husaynhakeem.hiltsample.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.husaynhakeem.hiltsample.R
import com.husaynhakeem.hiltsample.data.Post
import kotlinx.android.synthetic.main.item_post.view.*

class PostsAdapter(private val posts: List<Post>) :
    RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostViewHolder(parent)

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount() = posts.size

    inner class PostViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
    ) {

        fun bind(post: Post) {
            itemView.postTitle.text = post.title
            itemView.postBody.text = post.body
        }
    }
}