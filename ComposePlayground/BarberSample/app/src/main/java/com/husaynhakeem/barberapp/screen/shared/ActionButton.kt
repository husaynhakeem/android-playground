package com.husaynhakeem.barberapp.screen.shared

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke


@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    actionColor: Color,
    strokeWidth: Float = 8f,
    action: () -> Unit,
) {
    BoxWithConstraints(
        modifier = modifier
            .clickable { action() },
    ) {
        // Circle
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(CircleShape)
                .background(color = actionColor)
                .align(Alignment.CenterEnd),
        )

        // Arrow
        val arrowWidth = maxWidth * 0.3f // 30% of the allotted width
        val arrowOffset = maxHeight * 0.4f // The circle has a diameter of maxHeight
        FooterArrowIcon(
            modifier = Modifier
                .width(arrowWidth)
                .fillMaxHeight()
                .align(Alignment.CenterEnd)
                .offset(x = -arrowOffset),
            strokeWidth = strokeWidth,
        )
    }
}

@Composable
private fun FooterArrowIcon(
    modifier: Modifier = Modifier,
    strokeWidth: Float,
) {
    Canvas(
        modifier = modifier,
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val path = Path().let {
            // The arrow's horizontal line
            it.moveTo(x = 0f, y = canvasHeight / 2)
            it.lineTo(x = canvasWidth, y = canvasHeight / 2)

            // The arrow's top line
            it.lineTo(x = canvasWidth * 0.90f, y = canvasHeight * 0.35f)

            // The arrow's bottom line
            it.moveTo(x = canvasWidth, y = canvasHeight / 2)
            it.lineTo(x = canvasWidth * 0.90f, y = canvasHeight * 0.65f)

            return@let it
        }

        drawPath(
            path = path,
            color = Color.Black,
            style = Stroke(
                width = strokeWidth,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round,
            ),
        )
    }
}