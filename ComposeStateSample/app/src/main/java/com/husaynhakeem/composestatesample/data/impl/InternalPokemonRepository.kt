package com.husaynhakeem.composestatesample.data.impl

import android.annotation.SuppressLint
import androidx.core.util.Preconditions
import com.husaynhakeem.composestatesample.data.PokemonContainer
import com.husaynhakeem.composestatesample.data.PokemonRepository
import com.husaynhakeem.composestatesample.data.PokemonResult
import kotlinx.coroutines.delay
import kotlin.math.ceil
import kotlin.math.min

class InternalPokemonRepository(private val pokemonContainer: PokemonContainer) :
    PokemonRepository {

    private val allPokemon by lazy { pokemonContainer.get() }

    @SuppressLint("RestrictedApi")
    override suspend fun getPokemonResultFor(page: Int, pageSize: Int): PokemonResult {

        Preconditions.checkArgument(page > 0, "The page index must not be negative or zero.")
        Preconditions.checkArgument(pageSize > 0, "The page size must not be negative or zero.")

        // Fake an I/O operation that takes 1 second to complete
        delay(1_000)

        // Compute items to return
        val (fromIndex, toIndex) = computeStartAndEndIndices(page, pageSize)
        val items = allPokemon.safeSubList(fromIndex, toIndex)

        return PokemonResult(
            currentPage = page,
            totalPages = computeTotalPages(pageSize),
            items = items
        )
    }

    /**
     * Computes the total number of pages (or calls) needed to get all the pokemon given the page
     * size [pageSize].
     * E.g. Assuming there are 10 pokemons, the total pages are 1 if [pageSize] is equal to 10 and
     * 11, but 2 when [pageSize] is equal to 9.
     */
    private fun computeTotalPages(pageSize: Int) =
        ceil(allPokemon.size.toFloat() / pageSize).toInt()

    /**
     * Computes the start index and end index in order to retrieve a sublist of pokemons of size
     * [pageSize] from the page index [page].
     * E.g. Assuming there are 50 pokemons, [page] is 2 and [pageSize] is 10, the start index is 10,
     * and the end index is 20.
     */
    private fun computeStartAndEndIndices(page: Int, pageSize: Int): Pair<Int, Int> {
        val startIndex = (page - 1) * pageSize
        val endIndex = startIndex + pageSize
        return Pair(startIndex, endIndex)
    }

    /**
     * Safe version of [List.subList] that doesn't throw an [IndexOutOfBoundsException]. It returns
     * an empty list or truncates the sublist if either the start index [from] or the end index [to]
     * is greater than the last index.
     */
    private fun <T> List<T>.safeSubList(from: Int, to: Int): List<T> {
        if (from > lastIndex) {
            return emptyList()
        }

        val toIndex = min(size, to) // Exclusive index, use [size] instead of [lastIndex]
        return subList(from, toIndex)
    }
}