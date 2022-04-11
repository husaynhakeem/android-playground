package com.husaynhakeem.activityembeddingsample.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.husaynhakeem.activityembeddingsample.FABProvider
import com.husaynhakeem.activityembeddingsample.FABSplitListener
import com.husaynhakeem.activityembeddingsample.databinding.ActivityDetailsBinding
import com.husaynhakeem.activityembeddingsample.share.ShareActivity

class DetailsActivity : AppCompatActivity(), FABProvider {

    private lateinit var binding: ActivityDetailsBinding

    init {
        lifecycle.addObserver(FABSplitListener(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val letter = getLetter()
        binding.detailTextView.text = letter
        binding.shareTextView.setOnClickListener {
            ShareActivity.openShareScreenFor(letter, this)
        }
    }

    private fun getLetter(): String {
        return intent.getStringExtra(EXTRA_LETTER)
            ?: throw IllegalStateException("Must pass a letter to DetailActivity")
    }

    // FABProvider
    override val fab: FloatingActionButton by lazy { binding.detailFAB }
    override val activity: AppCompatActivity = this

    companion object {
        private const val EXTRA_LETTER = "extra-letter"

        fun openDetailScreenFor(letter: String, with: Context) {
            val intent = Intent(with, DetailsActivity::class.java)
            intent.putExtra(EXTRA_LETTER, letter)
            with.startActivity(intent)
        }
    }
}
