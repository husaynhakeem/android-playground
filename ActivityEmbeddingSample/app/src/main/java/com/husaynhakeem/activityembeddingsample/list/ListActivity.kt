package com.husaynhakeem.activityembeddingsample.list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.husaynhakeem.activityembeddingsample.FABProvider
import com.husaynhakeem.activityembeddingsample.FABSplitListener
import com.husaynhakeem.activityembeddingsample.databinding.ActivityListBinding
import com.husaynhakeem.activityembeddingsample.details.DetailsActivity

class ListActivity : AppCompatActivity(), FABProvider {

    private val viewModel: ListViewModel by viewModels()
    private lateinit var binding: ActivityListBinding

    init {
        lifecycle.addObserver(FABSplitListener(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListBinding.inflate(layoutInflater)
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
        DetailsActivity.openDetailScreenFor(letter, with = this)
    }

    // FABProvider
    override val fab: FloatingActionButton by lazy { binding.listFAB }
    override val activity: AppCompatActivity = this
}