package com.husaynhakeem.composestatesample.widget

import androidx.compose.runtime.Composable
import androidx.compose.runtime.onActive
import androidx.ui.tooling.preview.Preview
import com.husaynhakeem.composestatesample.data.Pokemon

@Composable
fun PokemonsGrid(
    pokemons: List<Pokemon>,
    spanCount: Int = 2,
    onLoadMorePokemons: () -> Unit
) {
    LazyGridForIndexed(items = pokemons, spanCount) { itemIndex, item ->
        if (itemIndex == pokemons.lastIndex) {
            onActive {
                onLoadMorePokemons()
            }
        }
        PokemonHolder(item)
    }
}

@Preview
@Composable
fun PokemonsGridPreview() {
    val pokemons = (1..10).map {
        Pokemon(id = it, name = "pokemon-$it", spriteUrl = "sprite-url-$it")
    }
    PokemonsGrid(pokemons = pokemons, onLoadMorePokemons = {})
}