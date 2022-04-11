package com.husaynhakeem.activityembeddingsample

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Consumer
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.window.core.ExperimentalWindowApi
import androidx.window.embedding.SplitController
import androidx.window.embedding.SplitInfo
import com.google.android.material.floatingactionbutton.FloatingActionButton

interface FABProvider {
    val fab: FloatingActionButton
    val activity: AppCompatActivity
}

@OptIn(ExperimentalWindowApi::class)
class FABSplitListener(private val fabProvider: FABProvider) : DefaultLifecycleObserver {

    private val fabSplitListener = Consumer<List<SplitInfo>> { splitInfoList ->
        val shouldHideFAB = splitInfoList.lastOrNull()
            ?.primaryActivityStack
            ?.contains(fabProvider.activity)
            ?: false
        fabProvider.fab.visibility = if (shouldHideFAB) View.GONE else View.VISIBLE
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        SplitController
            .getInstance()
            .addSplitListener(
                fabProvider.activity,
                fabProvider.activity.mainExecutor,
                fabSplitListener
            )
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        SplitController
            .getInstance()
            .removeSplitListener(fabSplitListener)
    }
}