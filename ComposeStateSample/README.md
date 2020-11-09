# Jetpack Compose State

Android sample app to learn about managing state when using Jetpack Compose. The app is a Pokedex that locally loads a list of pokemons and displays it on the UI, the data is paged, and the UI state is controlled by a [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel).

![Jetpack compose state - Sample](https://github.com/husaynhakeem/android-playground/blob/master/ComposeStateSample/art/android-jetpack-compose-state-sample.png)

The sample mainly showcases:
- Using a unidirectional data flow with ViewModel and Jetpack Compose
- Using stateless Composables
- Using stateful Composables via [`remember`](https://developer.android.com/reference/kotlin/androidx/compose/runtime/package-summary#remember) to internally store immutable values and handle state in a composable
- Creating custom layouts/composables. E.g. Creating a custom [lazy grid layout](https://github.com/husaynhakeem/android-playground/blob/master/ComposeStateSample/app/src/main/java/com/husaynhakeem/composestatesample/widget/LazyGridForIndexed.kt), the equivalent of [RecyclerView](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView) + [GridLayoutManager](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/GridLayoutManager).
- Loading paged data in a Composable and triggering recomposition.

### Resources used in this sample
- [Jetpack Compose - Managing state](https://developer.android.com/jetpack/compose/state)
- [List pagination with Jetpack Compose](https://medium.com/schibsted-tech-polska/list-pagination-with-jetpack-compose-6c25da053858)
