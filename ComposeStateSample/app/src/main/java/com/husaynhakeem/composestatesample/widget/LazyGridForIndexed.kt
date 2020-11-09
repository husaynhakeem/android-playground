package com.husaynhakeem.composestatesample.widget

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.ui.tooling.preview.Preview

/**
 * A vertically scrolling grid that only composes and lays out the currently visible items.
 *
 * It is similar to [LazyColumnForIndex] which provides both index and item as params for
 * [itemContent], with the difference that each element in the column is a row that contains
 * [rowSize] items.
 */
@Composable
fun <T> LazyGridForIndexed(
    items: List<T>,
    rowSize: Int = 1,
    itemContent: @Composable BoxScope.(index: Int, item: T) -> Unit
) {
    // Divide items into a grid, each row containing [rowSize] items
    val rows = items.windowed(rowSize, rowSize, true)

    LazyColumnForIndexed(rows) { indexOfRow, row ->

        // Every row should fill the entire width
        Row(modifier = Modifier.fillParentMaxWidth()) {

            // There are [rowSize] items per row, each has a weight of 1/rowSize
            val weight = 1f / rowSize

            // Layout items in row
            row.forEachIndexed { indexInsideRow, item ->
                Box(
                    modifier = Modifier
                        .weight(weight)
                        .aspectRatio(1F)
                ) {
                    itemContent(indexOfRow * rowSize + indexInsideRow, item)
                }
            }

            // Pad row with additional boxes if it isn't full
            if (row.size < rowSize) {
                for (i in 0 until (rowSize - row.size)) {
                    Box(modifier = Modifier.weight(weight))
                }
            }
        }
    }
}

@Preview
@Composable
fun LazyGridForPreview() {
    val data = (0..10).map(Integer::toString)
    LazyGridForIndexed(data, 2) { index, item ->
        Text(item)
    }
}
