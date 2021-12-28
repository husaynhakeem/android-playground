package com.husaynhakeem.glancesample

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import com.husaynhakeem.glancesample.ui.theme.GlanceSampleTheme
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel
            .widgets
            .onEach { updateWidgets(it) }
            .launchIn(lifecycleScope)
    }

    private fun updateWidgets(widgets: List<Widget>) {
        setContent {
            GlanceSampleTheme {
                Surface(color = MaterialTheme.colors.background) {
                    WidgetInfo(widgets = widgets)
                }
            }
        }
    }
}

@Composable
fun WidgetInfo(widgets: List<Widget>) {
    LazyColumn {
        items(widgets) { widget ->
            Text(text = widget.className)
        }
    }
}
