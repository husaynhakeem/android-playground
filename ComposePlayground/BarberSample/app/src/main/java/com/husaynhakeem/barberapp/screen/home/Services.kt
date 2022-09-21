package com.husaynhakeem.barberapp.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.husaynhakeem.barberapp.R
import com.husaynhakeem.barberapp.ui.theme.Black
import com.husaynhakeem.barberapp.ui.theme.OffWhite

data class Service(
    val id: Int,
    val label: String,
    val icon: ImageVector,
    val isSelected: Boolean,
)

@Composable
fun Services(
    modifier: Modifier = Modifier,
    services: List<Service>,
    showAll: () -> Unit,
    showService: (Service) -> Unit,
) {
    HomeSection(
        modifier = modifier,
        title = stringResource(id = R.string.services_section_header),
        onClick = { showAll() }) {
        LazyVerticalGrid(
            // Setting the grid's height to the screen's height is a hacky way to get around the
            // issue of using a lazy grid inside a column. One way around this is be to use
            // flow layouts from accomponist. Another is to render the entire screen in a
            // lazy vertical grid.
            // This approach works for now since there are only 4 rendered services.
            modifier = Modifier
                .heightIn(max = screenHeight),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            userScrollEnabled = false,
        ) {
            items(services) { service ->
                ServiceItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clickable { showService(service) },
                    service = service,
                )
            }
        }
    }
}

private val screenHeight: Dp
    @Composable
    get() = LocalConfiguration.current.screenHeightDp.dp

@Composable
private fun ServiceItem(
    modifier: Modifier,
    service: Service,
) {
    Card(
        modifier = modifier,
        elevation = 0.dp,
        backgroundColor = if (service.isSelected) Black else OffWhite,
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colors.surface)
                    .padding(16.dp),
            ) {
                Icon(
                    imageVector = service.icon,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSurface,
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = service.label,
            )
        }
    }
}