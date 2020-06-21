package com.husaynhakeem.hiltsample.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.husaynhakeem.hiltsample.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_posts.*

@AndroidEntryPoint
class PostsFragment : Fragment(R.layout.fragment_posts) {

    private val viewModel by viewModels<PostsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                PostsViewModel.State.Loading -> postsFlipper.displayedChild = LOADING
                PostsViewModel.State.Error -> postsFlipper.displayedChild = ERROR
                is PostsViewModel.State.Posts -> {
                    postsFlipper.displayedChild = POSTS
                    postsRecyclerView.layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
                    postsRecyclerView.adapter = PostsAdapter(state.posts)
                }
            }
        })
        postsRetryButton.setOnClickListener {
            viewModel.getPosts()
        }
    }

    companion object {
        private const val LOADING = 0
        private const val ERROR = 1
        private const val POSTS = 2
    }
}