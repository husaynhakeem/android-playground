package com.husaynhakeem.barberapp.screen.landing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.husaynhakeem.barberapp.R
import com.husaynhakeem.barberapp.screen.shared.ActionButton
import com.husaynhakeem.barberapp.ui.theme.Green

const val SCREEN_NAME_LANDING = "landing"

@Composable
fun LandingScreen(
    modifier: Modifier = Modifier,
    onNextClicked: () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        Header(
            modifier = Modifier.fillMaxWidth(),
        )
        Body(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f),
        )
        Footer(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            onNextClicked = onNextClicked,
        )
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier
                .width(32.dp),
            painter = painterResource(id = R.drawable.barber_landing_logo),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
        )
        Text(
            text = stringResource(id = R.string.lading_screen__title),
            style = MaterialTheme.typography.h6,
        )
    }
}

@Composable
private fun Body(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier
                .weight(1f, fill = false)
                .aspectRatio(1f),
            painter = painterResource(id = R.drawable.barber_landing_background),
            contentDescription = null,
            contentScale = ContentScale.Fit,
        )
        Spacer(modifier = Modifier.height(16.dp))
        AutoFitText(
            text = stringResource(id = R.string.lading_screen__body),
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            maxLines = 3,
        )
    }
}

@Composable
private fun Footer(
    modifier: Modifier = Modifier,
    onNextClicked: () -> Unit,
) {
    ActionButton(
        modifier = modifier,
        actionColor = Green,
        action = onNextClicked,
    )
}
