package com.husaynhakeem.sideeffectssample.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.husaynhakeem.sideeffectssample.log

const val REMEMBER_UPDATED_STATE = "Remember updated state"

private val colors = arrayOf("red", "green", "blue")

/**
 * [rememberUpdatedState] is a composable function that creates a reference to a value which can be
 * updated. When used in an effect, updates to the value don't trigger the effect to restart.
 *
 * It should be used when parameters or values computed during composition
 * are referenced by a long-lived lambda or object expression.
 */
@Composable
fun RememberUpdatedStateScreen() {
    var isDropdownMenuExpanded by remember { mutableStateOf(false) }
    var selectedColor: String? by remember { mutableStateOf(null) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        Box {
            DropdownMenu(
                expanded = isDropdownMenuExpanded,
                onDismissRequest = {
                    isDropdownMenuExpanded = false
                }) {
                colors.forEach { color ->
                    DropdownMenuItem(onClick = {
                        isDropdownMenuExpanded = false
                        selectedColor = color
                    }) {
                        Text(text = color)
                    }
                }
            }
            Text(
                text = selectedColor ?: "Selected color",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isDropdownMenuExpanded = true },
            )
        }
        SaveButton(
            modifier = Modifier.fillMaxWidth(),
            loggingData = selectedColor,
        )
    }
}

@Composable
fun SaveButton(
    modifier: Modifier = Modifier,
    loggingData: String?,
) {
    val updatedLoggingData by rememberUpdatedState(newValue = loggingData)
    val context = LocalContext.current

    Button(
        modifier = modifier,
        onClick = {
            Toast.makeText(context, "Saved color $loggingData", Toast.LENGTH_SHORT).show()
            log("Logging data: $updatedLoggingData")
        }) {
        Text(
            text = "Save",
        )
    }
}
