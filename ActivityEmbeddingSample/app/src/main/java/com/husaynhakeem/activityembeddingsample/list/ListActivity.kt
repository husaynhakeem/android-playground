package com.husaynhakeem.activityembeddingsample.list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.husaynhakeem.activityembeddingsample.databinding.ActivityListBinding
import com.husaynhakeem.activityembeddingsample.detail.DetailActivity

class ListActivity : AppCompatActivity() {

    private val viewModel: ListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        val binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val adapter = LettersAdapter(::openDetailScreenFor)
        binding.listRecyclerView.adapter = adapter
        binding.listRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.listRecyclerView.setHasFixedSize(true)

        viewModel.letters.observe(this) { letters ->
            adapter.submitList(letters)
        }
    }

    private fun openDetailScreenFor(letter: String) {
        DetailActivity.openDetailScreenFor(letter, with = this)
    }
}