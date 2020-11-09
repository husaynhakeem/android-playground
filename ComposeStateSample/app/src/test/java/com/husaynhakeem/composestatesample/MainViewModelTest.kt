package com.husaynhakeem.composestatesample

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.husaynhakeem.composestatesample.MainViewModel.State
import com.husaynhakeem.composestatesample.data.Pokemon
import com.husaynhakeem.composestatesample.data.fake.FakePokemonContainer
import com.husaynhakeem.composestatesample.data.impl.InternalPokemonRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun getInitialState() {
        // Arrange
        val allPokemon = generatePokemonList(4)
        val container = FakePokemonContainer().apply { setAllPokemon(allPokemon) }
        val repository = InternalPokemonRepository(container)
        val viewModel = MainViewModel(repository)
        val stateAssert = StateAssert(viewModel.state)

        // Assert
        stateAssert
            .hasState(State())
            .hasNoMoreStates()
    }

    @Test
    fun loadAllPokemons() {
        // Arrange
        val allPokemon = generatePokemonList(4)
        val container = FakePokemonContainer().apply { setAllPokemon(allPokemon) }
        val repository = InternalPokemonRepository(container)
        val viewModel = MainViewModel(repository)
        val stateAssert = StateAssert(viewModel.state)

        // Act
        viewModel.loadPokemons()
        mainDispatcherRule.advanceTimeBy(1_000)

        // Assert
        stateAssert
            .hasState(State())
            .hasState(State(isLoading = true))
            .hasState(State(pokemons = allPokemon, isLoading = false))
            .hasNoMoreStates()
    }

    @Test
    fun loadAllPokemonsWithPagination() {
        // Arrange
        val allPokemon = generatePokemonList(34)
        val container = FakePokemonContainer().apply { setAllPokemon(allPokemon) }
        val repository = InternalPokemonRepository(container)
        val viewModel = MainViewModel(repository)
        val stateAssert = StateAssert(viewModel.state)

        // Act
        // Load pokemons 0-9
        viewModel.loadPokemons()
        mainDispatcherRule.advanceTimeBy(1_000)

        // Load pokemons 10-19
        viewModel.loadPokemons()
        mainDispatcherRule.advanceTimeBy(1_000)

        // Load pokemons 20-29
        viewModel.loadPokemons()
        mainDispatcherRule.advanceTimeBy(1_000)

        // Load pokemons 30-33
        viewModel.loadPokemons()
        mainDispatcherRule.advanceTimeBy(1_000)

        // Assert
        stateAssert
            .hasState(State())
            .hasState(State(isLoading = true))
            .hasState(State(pokemons = allPokemon.subList(0, 10), isLoading = false))
            .hasState(State(pokemons = allPokemon.subList(0, 10), isLoading = true))
            .hasState(State(pokemons = allPokemon.subList(0, 20), isLoading = false))
            .hasState(State(pokemons = allPokemon.subList(0, 20), isLoading = true))
            .hasState(State(pokemons = allPokemon.subList(0, 30), isLoading = false))
            .hasState(State(pokemons = allPokemon.subList(0, 30), isLoading = true))
            .hasState(State(pokemons = allPokemon.subList(0, 34), isLoading = false))
            .hasNoMoreStates()
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

    private class StateAssert(private val stateEmitter: LiveData<State>) {

        private var index = 0
        private val states = mutableListOf<State?>()
        private val observer = Observer<State> { state -> states.add(state) }

        init {
            stateEmitter.observeForever(observer)
        }

        fun hasState(state: State): StateAssert {
            assertThat(states[index++]).isEqualTo(state)
            return this
        }

        fun hasNoMoreStates() {
            assertThat(index).isEqualTo(states.size)
            stateEmitter.removeObserver(observer)
        }
    }
}