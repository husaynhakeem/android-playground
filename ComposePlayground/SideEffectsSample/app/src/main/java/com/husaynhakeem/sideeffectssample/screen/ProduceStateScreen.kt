package com.husaynhakeem.sideeffectssample.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CrueltyFree
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


const val PRODUCE_STATE = "Produce state"

/**
 * [produceState] is syntax sugar on top of [LaunchedEffect], and can be used when feeding a
 * composable's state. The following are equivalent:
 *
 * - With [produceState]:
 * ```
 * val myState = produceState(myStateInitialValue, keys) {
 *     value = computation()
 * }
 * ```
 *
 * - With [LaunchedEffect]:
 * ```
 * var myState by remember { mutableStateOf(myStateInitialValue) }
 * LaunchedEffect(keys) {
 *     myState = computation()
 * }
 * ```
 */
@Composable
fun ProduceStateScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Image(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape),
                imageVector = loadProfilePicture().value ?: Icons.Default.Person,
                contentDescription = null,
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(text = "Husayn Hakeem")
                Text(text = "Status: Offline")
            }
        }
    }
}

@Composable
private fun loadProfilePicture(): State<ImageVector?> {
    return produceState<ImageVector?>(initialValue = null) {
        delay(1_000)
        value = Icons.Default.CrueltyFree
    }
}