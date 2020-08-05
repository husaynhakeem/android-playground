package com.husaynhakeem.daggersample.view.impl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.husaynhakeem.daggersample.R
import com.husaynhakeem.daggersample.model.News
import kotlinx.android.synthetic.main.list_item_all_news.view.*

class AllNewsAdapter(
    private val news: List<News>,
    private val onItemClickListener: (News) -> Unit
) :
    RecyclerView.Adapter<AllNewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(news[position], onItemClickListener)
    }

    override fun getItemCount() = news.size

    class ViewHolder(container: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(container.context)
            .inflate(R.layout.list_item_all_news, container, false)
    ) {

        fun bind(newsItem: News, onItemClickListener: (News) -> Unit) {
            itemView.allNewsListItemTitle.text = newsItem.title
            itemView.allNewsListItemText.text = newsItem.text
            itemView.setOnClickListener { onItemClickListener(newsItem) }
        }
    }

}
