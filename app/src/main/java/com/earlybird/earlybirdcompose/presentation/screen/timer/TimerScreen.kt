package com.earlybird.earlybirdcompose.presentation.screen.timer

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.earlybird.earlybirdcompose.presentation.screen.timer.component.SlideInImage
import com.earlybird.earlybirdcompose.presentation.screen.timer.component.TimerContainer
import com.earlybird.earlybirdcompose.presentation.screen.timer.component.TimerContent
import com.earlybird.earlybirdcompose.presentation.screen.timer.component.TimerDoneContent
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdComposeTheme

@Composable
fun TimerScreen(
    content: String,
    buttonContent: String,
    durationMillis: Int = 2 * 60 * 1000,
    isFinished: Boolean = false,
    onTimerDoneClick: () -> Unit = {}
){
    Log.d("overlayService", "$content , $buttonContent , $durationMillis , $isFinished")
    val progress = remember { Animatable(if (isFinished) 1f else 0f) }
    // 타이머 시작
    LaunchedEffect(Unit) {
        if(!isFinished){
            progress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = durationMillis, easing = LinearEasing)
            )
        }
    }
    val backgroundColor = when {
        progress.value < 0.5f -> Brush.verticalGradient(
            colors = listOf(Color(0xFFF7F7F7), Color(0xFFD9F8FF))
        )
        progress.value < 1f -> Brush.verticalGradient(
            colors = listOf(Color(0xFFFFF3E0), Color(0xFFFFF776)) // 절반 이후 색
        )
        else -> Brush.verticalGradient(
            colors = listOf(Color(0xFFFFFBEC), Color(0xFFFFFBEC)) // 종료 색
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundColor)
    ){
        SlideInImage(progress = progress.value)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp, start = 26.dp, end = 26.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircleTimer(
                content = content,
                buttonContent = buttonContent,
                progress = progress.value,
                durationMillis = durationMillis,
                onTimerDoneClick = onTimerDoneClick
            )
        }
    }
}

@Composable
fun CircleTimer(
    content: String,
    buttonContent: String,
    progress: Float,
    durationMillis: Int, // 2분
    onTimerDoneClick: () -> Unit
) {
    val color = if (progress < 0.5f) Color(0xFF4DA6FF) else Color(0xFFFF6666)
    val isFinished = progress >= 1f

    TimerContainer {
        if (!isFinished) {
            TimerContent(progress, durationMillis, color)
        } else {
            TimerDoneContent(
                content = content,
                buttonContent = buttonContent,
                onClick = onTimerDoneClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimerScreenPreview(){
    EarlyBirdComposeTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            TimerContainer {
                TimerDoneContent(
                    content = "우와! 우리가 해냈다\n다음에도 같이 하자!!",
                    buttonContent = "완료!",
                    onClick = { }
                )
            }
        }
    }
}