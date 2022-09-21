package com.husaynhakeem.barberapp.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.husaynhakeem.barberapp.screen.shared.ActionButton
import com.husaynhakeem.barberapp.ui.theme.Green
import com.husaynhakeem.barberapp.ui.theme.White

@Composable
fun PromoCard(
    modifier: Modifier = Modifier,
    label: String,
    showPromo: () -> Unit,
) {
    Card(
        modifier = modifier.clickable { showPromo() },
        backgroundColor = Green,
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.h6,
            )
            ActionButton(
                modifier = Modifier
                    .height(32.dp)
                    .fillMaxWidth(),
                strokeWidth = 6f,
                actionColor = White,
                action = {},
            )
        }
    }
}