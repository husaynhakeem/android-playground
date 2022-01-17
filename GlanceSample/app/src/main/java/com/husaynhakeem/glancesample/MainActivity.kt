package com.husaynhakeem.glancesample

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
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
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = getString(R.string.app_name)) }
                        )
                    }
                ) {
                    WidgetInfo(modifier = Modifier.padding(it), widgets = widgets)
                }
            }
        }
    }
}

@Composable
fun WidgetInfo(
    modifier: Modifier = Modifier,
    widgets: List<Widget>
) {
    LazyColumn(modifier = modifier) {
        items(widgets) { widget ->
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = widget.className, textDecoration = TextDecoration.Underline)
                Box(modifier = Modifier.padding(start = 8.dp)) {
                    if (widget.metadata.isEmpty()) {
                        WidgetNoMetadata()
                    } else {
                        WidgetMetadata(widget.metadata)
                    }
                }
            }
        }
    }
}

@Composable
fun WidgetNoMetadata() {
    Text(text = "No metadata")
}

@Composable
fun WidgetMetadata(metadata: List<WidgetMetadata>) {
    Column {
        metadata.forEach {
            Text(text = it.id.toString())
            Text(text = it.sizes.toString())
        }
    }
}