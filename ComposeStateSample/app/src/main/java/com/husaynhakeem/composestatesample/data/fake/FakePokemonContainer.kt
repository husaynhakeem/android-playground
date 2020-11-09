package com.husaynhakeem.composestatesample.data.fake

import com.husaynhakeem.composestatesample.data.Pokemon
import com.husaynhakeem.composestatesample.data.PokemonContainer

class FakePokemonContainer : PokemonContainer {

    private val allPokemon: MutableList<Pokemon> = mutableListOf()

    override fun get(): List<Pokemon> {
        return allPokemon.toList()
    }

    fun setAllPokemon(allPokemon: List<Pokemon>) {
        this.allPokemon.clear()
        this.allPokemon.addAll(allPokemon)
    }
}