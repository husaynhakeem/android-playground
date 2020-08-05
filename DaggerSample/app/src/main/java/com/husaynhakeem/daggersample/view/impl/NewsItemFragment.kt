package com.husaynhakeem.daggersample.view.impl

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.husaynhakeem.daggersample.DaggerApp
import com.husaynhakeem.daggersample.R
import com.husaynhakeem.daggersample.model.News
import com.husaynhakeem.daggersample.presenter.NewsItemPresenter
import com.husaynhakeem.daggersample.view.NewsItemView
import kotlinx.android.synthetic.main.fragment_news_item.*
import javax.inject.Inject

class NewsItemFragment : Fragment(R.layout.fragment_news_item), NewsItemView {

    @Inject
    lateinit var presenter: NewsItemPresenter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireContext().applicationContext as DaggerApp).appComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bind(this)
        val id = arguments?.getLong(KEY_NEWS_ITEM_ID)
        if (id == null) {
            displayError("News item id is null")
        } else {
            presenter.getNewsItem(id)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unbind()
    }

    override fun displayNewsItem(news: News) {
        newsItemTitle.text = news.title
        newsItemText.text = news.text
    }

    override fun onNewsItemNotFound(id: Long) {
        displayError("Error getting the news item with id $id")
    }

    private fun displayError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {

        private const val KEY_NEWS_ITEM_ID = "key-news-item-id"

        @JvmStatic
        fun instance(id: Long): NewsItemFragment {
            return NewsItemFragment().apply {
                arguments = bundleOf(KEY_NEWS_ITEM_ID to id)
            }
        }
    }
}
