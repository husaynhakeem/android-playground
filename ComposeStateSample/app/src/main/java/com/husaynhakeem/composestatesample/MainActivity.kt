package com.husaynhakeem.composestatesample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import coil.Coil
import coil.ImageLoader
import coil.util.DebugLogger
import com.husaynhakeem.composestatesample.ui.ComposeStateSampleTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set debug logger to monitor image loading logs
        Coil.setImageLoader(
            ImageLoader.Builder(this)
                .logger(DebugLogger())
                .build()
        )

        // Set up viewModel and UI
        val factory = MainViewModel.Factory()
        val viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        setContent {
            ComposeStateSampleTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Pokedex - Jetpack Compose")
                            },
                            elevation = 12.dp
                        )
                    }
                ) {
                    PokemonsLayout(viewModel)
                }
            }
        }
    }
}
