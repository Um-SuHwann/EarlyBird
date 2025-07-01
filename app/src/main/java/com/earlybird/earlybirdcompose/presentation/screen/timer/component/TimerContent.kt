package com.earlybird.earlybirdcompose.presentation.screen.timer.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 실제 타이머 기능을 하는 compose
@Composable
fun TimerContent(progress: Float, durationMillis: Int, color: Color) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val backgroundStrokeWidth = 9.dp.toPx()
        val progressStrokeWidth = 18.dp.toPx()
        val radius = size.minDimension / 2 - progressStrokeWidth / 2
        val center = Offset(size.width / 2, size.height / 2)

        drawCircle(
            color = color,
            center = center,
            radius = radius,
            style = Stroke(backgroundStrokeWidth)
        )
        drawArc(
            color = color,
            startAngle = -90f,
            sweepAngle = 360f * progress,
            useCenter = false,
            style = Stroke(width = progressStrokeWidth, cap = StrokeCap.Round),
            size = Size(radius * 2, radius * 2),
            topLeft = Offset(center.x - radius, center.y - radius)
        )
    }

    val remainingSeconds = (1f - progress) * durationMillis / 1000f
    val totalSeconds = remainingSeconds.toInt()
    val decimal = ((remainingSeconds - totalSeconds) * 100).toInt()
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60

    val timeText = buildAnnotatedString {
        append(String.format("%02d:%02d:", minutes, seconds))
        withStyle(style = SpanStyle(fontSize = 30.sp)) {
            append(String.format("%02d", decimal))
        }
    }

    Text(
        text = timeText,
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold,
        color = color
    )
}