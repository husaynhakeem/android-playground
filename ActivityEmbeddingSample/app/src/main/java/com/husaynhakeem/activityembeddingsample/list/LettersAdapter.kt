package com.husaynhakeem.activityembeddingsample.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.husaynhakeem.activityembeddingsample.databinding.ItemListBinding

class LettersAdapter(private val onItemClickListener: (String) -> Unit) :
    ListAdapter<String, LettersAdapter.ViewHolder>(LettersDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClickListener)
    }

    class ViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(letter: String, onItemClickListener: (String) -> Unit) {
            binding.listItemTextView.text = letter
            binding.root.setOnClickListener {
                onItemClickListener.invoke(letter)
            }
        }
    }

    object LettersDiffUtil : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}