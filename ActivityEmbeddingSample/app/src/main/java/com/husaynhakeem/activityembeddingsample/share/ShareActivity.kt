package com.husaynhakeem.activityembeddingsample.share

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.husaynhakeem.activityembeddingsample.R
import com.husaynhakeem.activityembeddingsample.databinding.ActivityShareBinding

class ShareActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityShareBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.shareTextView.text = getString(R.string.label_sharing, getLetter())
    }

    private fun getLetter(): String {
        return intent.getStringExtra(EXTRA_LETTER)
            ?: throw IllegalStateException("Must pass a letter to ShareActivity")
    }

    companion object {
        private const val EXTRA_LETTER = "extra-letter"

        fun openShareScreenFor(letter: String, with: Context) {
            val intent = Intent(with, ShareActivity::class.java)
            intent.putExtra(EXTRA_LETTER, letter)
            with.startActivity(intent)
        }
    }
}