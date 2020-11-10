package com.husaynhakeem.composestatesample

import androidx.lifecycle.*
import com.husaynhakeem.composestatesample.data.Pokemon
import com.husaynhakeem.composestatesample.data.PokemonRepository
import com.husaynhakeem.composestatesample.data.impl.InMemoryPokemonContainer
import com.husaynhakeem.composestatesample.data.impl.InternalPokemonRepository
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

class MainViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {

    private val _state = MutableLiveData(State())
    val state: LiveData<State> = _state

    // Todo: Group inside pagination state
    private val currentPage = AtomicInteger(INITIAL_PAGE)
    private val totalPages = AtomicInteger(INITIAL_TOTAL_PAGES)
    private val isQueryInProgress = AtomicBoolean(false)

    fun loadPokemons() {
        // Do not load more pokemons if they have already all been loaded, or if a query is in
        // progress.
        if (currentPage.get() > totalPages.get() || isQueryInProgress.get()) {
            return
        }

        viewModelScope.launch {
            _state.postValue(State(getPokemonsFromState(), true))
            isQueryInProgress.set(true)

            val pokemonResult = pokemonRepository.getPokemonResultFor(currentPage.get(), PAGE_SIZE)

            // Update current page and total pages from the response
            currentPage.set(pokemonResult.currentPage + 1)
            totalPages.set(pokemonResult.totalPages)

            // Add list of pokemons from response to current list of pokemons, then update state
            _state.postValue(State(getPokemonsFromState() + pokemonResult.items, false))

            isQueryInProgress.set(false)
        }
    }

    private fun getPokemonsFromState() = _state.value!!.pokemons.toList()

    data class State(val pokemons: List<Pokemon> = emptyList(), val isLoading: Boolean = false) {

        fun isInitialState() = pokemons.isEmpty() && !isLoading
    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val container = InMemoryPokemonContainer()
            val repository = InternalPokemonRepository(container)
            return MainViewModel(repository) as T
        }
    }

    companion object {
        private const val INITIAL_PAGE = 1
        private const val INITIAL_TOTAL_PAGES = Int.MAX_VALUE
        private const val PAGE_SIZE = 10
    }
}