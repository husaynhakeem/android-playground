package com.husaynhakeem.daggersample.view.impl

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.husaynhakeem.daggersample.DaggerApp
import com.husaynhakeem.daggersample.R
import com.husaynhakeem.daggersample.model.News
import com.husaynhakeem.daggersample.presenter.AllNewsPresenter
import com.husaynhakeem.daggersample.view.AllNewsView
import kotlinx.android.synthetic.main.fragment_all_news.*
import javax.inject.Inject

class AllNewsFragment : Fragment(R.layout.fragment_all_news), AllNewsView {

    @Inject
    lateinit var presenter: AllNewsPresenter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireContext().applicationContext as DaggerApp).appComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bind(this)
        presenter.getAllNews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unbind()
    }

    override fun displayAllNews(news: List<News>) {
        allNewsRecyclerView.setHasFixedSize(true)
        allNewsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        allNewsRecyclerView.adapter = AllNewsAdapter(news) { newsItem ->
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.content, NewsItemFragment.instance(newsItem.id))
                .addToBackStack("allNews")
                .commit()
        }
    }
}