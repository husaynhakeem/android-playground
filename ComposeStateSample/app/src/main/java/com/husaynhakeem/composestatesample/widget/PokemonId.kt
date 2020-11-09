package com.husaynhakeem.composestatesample.widget

import androidx.compose.foundation.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.ui.tooling.preview.Preview

@Composable
fun PokemonId(
    id: Int,
    modifier: Modifier = Modifier
) {
    val paddedId = id.toString().padStart(3, '0')
    val formattedId = "#$paddedId"
    Text(
        text = formattedId,
        color = Color.White,
        modifier = modifier
    )
}

@Preview(name = "Pokemon id - 1 digit")
@Composable
fun PokemonIdPreview_oneDigit() {
    PokemonId(id = 3)
}

@Preview(name = "Pokemon id - 2 digits")
@Composable
fun PokemonIdPreview_twoDigits() {
    PokemonId(id = 13)
}

@Preview(name = "Pokemon id - 3 digits")
@Composable
fun PokemonIdPreview_threeDigits() {
    PokemonId(id = 139)
}
