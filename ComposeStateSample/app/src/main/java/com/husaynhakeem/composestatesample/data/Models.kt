package com.husaynhakeem.composestatesample.data

data class PokemonResult(
    val currentPage: Int,
    val totalPages: Int,
    val items: List<Pokemon>
)

data class Pokemon(
    val id: Int,
    val name: String,
    val spriteUrl: String
)