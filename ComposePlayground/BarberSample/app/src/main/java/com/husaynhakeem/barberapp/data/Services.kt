package com.husaynhakeem.barberapp.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Sanitizer
import androidx.compose.runtime.Composable
import com.husaynhakeem.barberapp.screen.home.Service

val services: List<Service>
    @Composable
    get() {
        return listOf(
            Service(
                id = 1,
                label = "Classic shaving",
                icon = Icons.Default.ContentCut,
                isSelected = true,
            ),
            Service(
                id = 2,
                label = "Hair care",
                icon = Icons.Default.Sanitizer,
                isSelected = false,
            ),
            Service(
                id = 3,
                label = "Beard trimming",
                icon = Icons.Default.AutoFixHigh,
                isSelected = false,
            ),
            Service(
                id = 4,
                label = "Classic haircut",
                icon = Icons.Default.ContentCut,
                isSelected = false,
            ),
            Service(
                id = 1,
                label = "Classic shaving",
                icon = Icons.Default.ContentCut,
                isSelected = true,
            ),
            Service(
                id = 2,
                label = "Hair care",
                icon = Icons.Default.Sanitizer,
                isSelected = false,
            ),
            Service(
                id = 3,
                label = "Beard trimming",
                icon = Icons.Default.AutoFixHigh,
                isSelected = false,
            ),
            Service(
                id = 4,
                label = "Classic haircut",
                icon = Icons.Default.ContentCut,
                isSelected = false,
            ),
            Service(
                id = 1,
                label = "Classic shaving",
                icon = Icons.Default.ContentCut,
                isSelected = true,
            ),
            Service(
                id = 2,
                label = "Hair care",
                icon = Icons.Default.Sanitizer,
                isSelected = false,
            ),
            Service(
                id = 3,
                label = "Beard trimming",
                icon = Icons.Default.AutoFixHigh,
                isSelected = false,
            ),
            Service(
                id = 4,
                label = "Classic haircut",
                icon = Icons.Default.ContentCut,
                isSelected = false,
            ),
            Service(
                id = 1,
                label = "Classic shaving",
                icon = Icons.Default.ContentCut,
                isSelected = true,
            ),
            Service(
                id = 2,
                label = "Hair care",
                icon = Icons.Default.Sanitizer,
                isSelected = false,
            ),
            Service(
                id = 3,
                label = "Beard trimming",
                icon = Icons.Default.AutoFixHigh,
                isSelected = false,
            ),
            Service(
                id = 4,
                label = "Classic haircut",
                icon = Icons.Default.ContentCut,
                isSelected = false,
            ),
        )
    }