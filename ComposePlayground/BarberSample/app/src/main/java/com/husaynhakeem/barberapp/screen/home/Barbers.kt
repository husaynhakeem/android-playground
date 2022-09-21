package com.husaynhakeem.barberapp.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.husaynhakeem.barberapp.R

private const val BARBERS_PREVIEW_COUNT = 4

data class Barber(
    val id: Long,
    val imageUrl: String,
)

@Composable
fun Barbers(
    modifier: Modifier = Modifier,
    barbers: List<Barber>,
    showAll: () -> Unit,
    showBarber: (Barber) -> Unit,
) {
    HomeSection(
        modifier = modifier,
        title = stringResource(id = R.string.barbers_section_header),
        onClick = { showAll() }) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            barbers
                .take(BARBERS_PREVIEW_COUNT)
                .map { barber ->
                    BarberItem(barber, showBarber)
                }
        }
    }
}

@Composable
private fun BarberItem(
    barber: Barber,
    showBarber: (Barber) -> Unit
) {
    AsyncImage(
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .clickable { showBarber(barber) },
        model = barber.imageUrl,
        contentScale = ContentScale.Crop,
        contentDescription = null,
    )
}


