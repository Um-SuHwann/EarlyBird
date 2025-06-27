package com.earlybird.earlybirdcompose.presentation.screen.timer

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.earlybird.earlybirdcompose.R
import com.earlybird.earlybirdcompose.presentation.screen.main.MainScreen
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdComposeTheme
import kotlinx.coroutines.delay
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdTheme

@Composable
fun TimerScreen(
    onTimerFinish: () -> Unit = {}
){
    val progress = remember { Animatable(0f) }
    val durationMillis = 2*10*1000
    // 타이머 시작
    LaunchedEffect(Unit) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = durationMillis, easing = LinearEasing)
        )
        //타이머 종료
        onTimerFinish()
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
                .padding(top = 8.dp, start = 26.dp, end = 26.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircleTimer(progress.value, durationMillis)
        }
    }
}
@Composable
fun CircleTimer(
    progress: Float,
    durationMillis: Int // 2분
) {
    val isBeforeHalf = progress < 0.5f
    val activeColor = if (isBeforeHalf) Color(0xFF4DA6FF) else Color(0xFFFF6666) // 파랑 → 빨강
    Surface(
        shape = RoundedCornerShape(100.dp),
        shadowElevation = 2.dp
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1f) // 정사각형 유지
                .fillMaxWidth()
                .background(color = Color(0xFFFFFFFF))
                .padding(20.dp), // 너비 비율 조정
            contentAlignment = Alignment.Center,
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val backgroundStrokeWidth = 9.dp.toPx()   // 얇은 배경 원
                val progressStrokeWidth = 18.dp.toPx()    // 두꺼운 진행 원
                val radius = size.minDimension / 2 - progressStrokeWidth / 2
                val center = Offset(size.width / 2, size.height / 2)

                // 배경 원 (회색)
                drawCircle(
                    color = activeColor,
                    center = center,
                    radius = radius,
                    style = Stroke(backgroundStrokeWidth)
                )
                // 진행 원 (파란색)
                drawArc(
                    color = activeColor,
                    startAngle = -90f, // 위에서 시작
                    sweepAngle = 360f * progress,
                    useCenter = false,
                    style = Stroke(
                        width = progressStrokeWidth,
                        cap = StrokeCap.Round
                    ),
                    size = Size(radius * 2, radius * 2),
                    topLeft = Offset(
                        center.x - radius,
                        center.y - radius
                    )
                )
            }
            // 남은 시간 텍스트 (옵션)
            val remainingSeconds = (1f - progress) * durationMillis / 1000f
            val totalSeconds = remainingSeconds.toInt()
            val decimal = ((remainingSeconds - totalSeconds) * 100).toInt() // 소수점 둘째 자리

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
                color = activeColor
            )
        }
    }

}
@Composable
fun SlideInImage(progress: Float) {
    val offsetY = remember { Animatable(250.dp, Dp.VectorConverter) }
    var hasAnimatedHalfway by remember { mutableStateOf(false) }
    var hasAnimatedFinished by remember { mutableStateOf(false) }

    val readyPainter = painterResource(R.drawable.timer_bird_ready)
    val startPainter = painterResource(R.drawable.timer_bird_start)
    val endPainter = painterResource(R.drawable.timer_bird_end)

    val isHalfway = progress >= 0.5f
    val isFinished = progress >= 1f

    // 초기 진입 애니메이션 (위로 슬라이드)
    LaunchedEffect(Unit) {
        delay(500)
        offsetY.animateTo(0.dp, animationSpec = tween(durationMillis = 1000))
    }

    // 절반 시점에 아래로 갔다가 다시 위로 올라오기
    LaunchedEffect(isHalfway) {
        if (isHalfway && !hasAnimatedHalfway) {
            offsetY.animateTo(700.dp, animationSpec = tween(1000)) // 아래로
            hasAnimatedHalfway = true
            delay(200)
            offsetY.animateTo(0.dp, animationSpec = tween(1000))   // 다시 위로
        }
    }
    // 완료 시점에 아래로 갔다가 다시 위로 올라오기
    LaunchedEffect(isFinished) {
        if (isFinished && !hasAnimatedFinished) {
            offsetY.animateTo(700.dp, animationSpec = tween(1000)) // 아래로
            hasAnimatedFinished = true
            delay(200)
            offsetY.animateTo(0.dp, animationSpec = tween(1000))   // 다시 위로
        }
    }
    val painter = when {
        hasAnimatedFinished -> endPainter
        hasAnimatedHalfway -> startPainter
        else -> readyPainter
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Crossfade(
            targetState = painter,
            animationSpec = tween(durationMillis = 200),
            label = "imageCrossfade")
            { painter ->
            Image(
                painter = painter,
                contentDescription = "timer 캐릭터",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = offsetY.value)
                    .fillMaxWidth(1f),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimerScreenPreview(){
    EarlyBirdComposeTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            TimerScreen()
        }
    }
}