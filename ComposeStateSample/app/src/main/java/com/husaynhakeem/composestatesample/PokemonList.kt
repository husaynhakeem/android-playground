package com.husaynhakeem.composestatesample

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.husaynhakeem.composestatesample.ui.ComposeStateSampleTheme
import com.husaynhakeem.composestatesample.widget.PokemonsGrid

@Composable
fun PokemonsLayout(viewModel: MainViewModel) {
    val observedState by viewModel.state.observeAsState()
    val state = observedState ?: return
    if (state.isInitialState()) {
        viewModel.loadPokemons()
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // Pokemons
        PokemonsGrid(pokemons = state.pokemons, onLoadMorePokemons = { viewModel.loadPokemons() })

        // Loading
        if (state.isLoading) {
            CircularProgressIndicator(
                Modifier
                    .align(alignment = Alignment.Center)
                    .padding(80.dp)
            )
        }
    }
}


@Preview
@Composable
fun PokemonListPreview() {
    ComposeStateSampleTheme {
    }
}
