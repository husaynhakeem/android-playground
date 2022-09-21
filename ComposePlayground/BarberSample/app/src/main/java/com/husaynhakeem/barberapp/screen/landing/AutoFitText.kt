package com.husaynhakeem.barberapp.screen.landing

import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun AutoFitText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle,
    fontWeight: FontWeight,
    textAlign: TextAlign,
    maxLines: Int,
) {
    var actualStyle by remember { mutableStateOf(style) }
    Text(
        modifier = modifier,
        text = text,
        style = actualStyle,
        textAlign = textAlign,
        fontWeight = fontWeight,
        maxLines = maxLines,
        onTextLayout = { textLayoutResult ->
            if (textLayoutResult.hasVisualOverflow) {
                println("Recomposing")
                actualStyle = actualStyle.copy(fontSize = actualStyle.fontSize * 0.9)
            }
        }
    )
}