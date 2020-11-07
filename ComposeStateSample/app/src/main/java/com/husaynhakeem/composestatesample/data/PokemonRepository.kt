package com.husaynhakeem.composestatesample.data

interface PokemonRepository {

    suspend fun getPokemonResultFor(page: Int, pageSize: Int): PokemonResult
}