package com.husaynhakeem.composestatesample.widget

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.husaynhakeem.composestatesample.data.Pokemon

@Composable
fun PokemonHolder(pokemon: Pokemon) {
    val backgroundColor = remember { mutableStateOf(Color.White) }

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundColor.value)
                .padding(16.dp)
        ) {
            // Pokemon id
            PokemonId(id = pokemon.id)

            // Pokemon sprite
            PokemonSprite(
                spriteUrl = pokemon.spriteUrl,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                onComputeMutedColor = { color -> backgroundColor.value = color })

            // Pokemon name
            Text(
                text = pokemon.name,
                color = Color.White,
                modifier = Modifier
                    .padding(8.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview
@Composable
fun PokemonHolderPreview() {
    PokemonHolder(pokemon = Pokemon(id = 1, name = "Pikachu", spriteUrl = "url"))
}
