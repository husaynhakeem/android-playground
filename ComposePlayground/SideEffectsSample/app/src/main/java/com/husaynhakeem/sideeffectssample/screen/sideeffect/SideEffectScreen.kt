package com.husaynhakeem.sideeffectssample.screen.sideeffect

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

const val SIDE_EFFECT = "Side effect"

enum class PasswordStrength {
    STRONG, MEDIUM, WEAK
}

@Composable
fun SideEffectScreen(
    passwordStrengthCallback: (PasswordStrength) -> Unit,
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue()) }

    SideEffect {
        val passwordStrength = when {
            textFieldValue.text.length < 4 -> PasswordStrength.WEAK
            textFieldValue.text.length < 8 -> PasswordStrength.MEDIUM
            else -> PasswordStrength.STRONG
        }
        passwordStrengthCallback.invoke(passwordStrength)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center,
    ) {
        TextField(
            value = textFieldValue,
            onValueChange = { textFieldValue = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Password") },
        )
    }
}