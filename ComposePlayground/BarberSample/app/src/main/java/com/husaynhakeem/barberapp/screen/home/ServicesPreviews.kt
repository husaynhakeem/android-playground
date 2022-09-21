package com.husaynhakeem.barberapp.screen.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.husaynhakeem.barberapp.data.services

@Preview(showBackground = true)
@Composable
fun Preview_Services() {
    Services(
        modifier = Modifier.fillMaxWidth(),
        services = services,
        showAll = {},
        showService = {},
    )
}