package com.husaynhakeem.composestatesample.data.impl

import com.google.common.truth.Truth.assertThat
import com.husaynhakeem.composestatesample.data.Pokemon
import com.husaynhakeem.composestatesample.data.fake.FakePokemonContainer
import kotlinx.coroutines.runBlocking
import org.junit.Test

class InternalPokemonRepositoryTest {

    @Test
    fun getCurrentPage() {
        // Arrange
        val container = FakePokemonContainer()
        val repository = InternalPokemonRepository(container)

        // Act
        val pokemonResult = runBlocking { repository.getPokemonResultFor(3, 10) }

        // Assert
        assertThat(pokemonResult.currentPage).isEqualTo(3)
    }

    @Test
    fun getTotalPages_whenAllPokemonSizeMultipleOfPageSize() {
        // Arrange
        val container = FakePokemonContainer().apply { setAllPokemon(generatePokemonList(10)) }
        val repository = InternalPokemonRepository(container)

        // Act
        val pokemonResult = runBlocking { repository.getPokemonResultFor(3, 5) }

        // Assert
        assertThat(pokemonResult.totalPages).isEqualTo(2)
    }

    @Test
    fun getTotalPages_whenAllPokemonSizeNotMultipleOfPageSize() {
        // Arrange
        val container = FakePokemonContainer().apply { setAllPokemon(generatePokemonList(10)) }
        val repository = InternalPokemonRepository(container)

        // Act
        val pokemonResult = runBlocking { repository.getPokemonResultFor(3, 9) }

        // Assert
        assertThat(pokemonResult.totalPages).isEqualTo(2)
    }

    @Test
    fun getTotalPages_whenAllPokemonSizeSmallerThanPageSize() {
        // Arrange
        val container = FakePokemonContainer().apply { setAllPokemon(generatePokemonList(10)) }
        val repository = InternalPokemonRepository(container)

        // Act
        val pokemonResult = runBlocking { repository.getPokemonResultFor(3, 19) }

        // Assert
        assertThat(pokemonResult.totalPages).isEqualTo(1)
    }

    @Test
    fun getPokemonItems_forFirstPage() {
        // Arrange
        val allPokemon = generatePokemonList(10)
        val container = FakePokemonContainer().apply { setAllPokemon(allPokemon) }
        val repository = InternalPokemonRepository(container)

        // Act
        val pokemonResult = runBlocking { repository.getPokemonResultFor(1, 5) }

        // Assert
        assertThat(pokemonResult.items).containsExactlyElementsIn(allPokemon.subList(0, 5))
    }

    @Test
    fun getPokemonItems_forSecondPage() {
        // Arrange
        val allPokemon = generatePokemonList(10)
        val container = FakePokemonContainer().apply { setAllPokemon(allPokemon) }
        val repository = InternalPokemonRepository(container)

        // Act
        val pokemonResult = runBlocking { repository.getPokemonResultFor(2, 5) }

        // Assert
        assertThat(pokemonResult.items).containsExactlyElementsIn(allPokemon.subList(5, 10))
    }

    @Test
    fun getPokemonItems_whenPageSizeGreaterThanRemainingItems() {
        // Arrange
        val allPokemon = generatePokemonList(20)
        val container = FakePokemonContainer().apply { setAllPokemon(allPokemon) }
        val repository = InternalPokemonRepository(container)

        // Act
        val pokemonResult = runBlocking { repository.getPokemonResultFor(4, 6) }

        // Assert
        assertThat(pokemonResult.items).containsExactlyElementsIn(allPokemon.subList(18, 20))
    }

    @Test(expected = IllegalArgumentException::class)
    fun fail_whenPageIndexIsZero() {
        // Arrange
        val container = FakePokemonContainer()
        val repository = InternalPokemonRepository(container)

        // Act
        runBlocking { repository.getPokemonResultFor(0, 19) }
    }

    @Test(expected = IllegalArgumentException::class)
    fun fail_whenPageIndexIsNegative() {
        // Arrange
        val container = FakePokemonContainer()
        val repository = InternalPokemonRepository(container)

        // Act
        runBlocking { repository.getPokemonResultFor(-3, 19) }
    }

    @Test(expected = IllegalArgumentException::class)
    fun fail_whenPageSizeIsZero() {
        // Arrange
        val container = FakePokemonContainer()
        val repository = InternalPokemonRepository(container)

        // Act
        runBlocking { repository.getPokemonResultFor(1, 0) }
    }

    @Test(expected = IllegalArgumentException::class)
    fun fail_whenPageSizeIsNegative() {
        // Arrange
        val container = FakePokemonContainer()
        val repository = InternalPokemonRepository(container)

        // Act
        runBlocking { repository.getPokemonResultFor(1, -10) }
    }

    private fun generatePokemonList(size: Int): List<Pokemon> {
        return (1..size).map { index ->
            Pokemon(
                id = index,
                name = "Pokemon-$index",
                spriteUrl = "sprite-url-$index"
            )
        }
    }
}