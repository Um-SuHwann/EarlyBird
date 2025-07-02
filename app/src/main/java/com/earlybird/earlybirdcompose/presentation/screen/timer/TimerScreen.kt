package com.earlybird.earlybirdcompose.presentation.screen.timer

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
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
import androidx.compose.ui.unit.sp
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdTheme

@Composable
fun TimerScreen(){
    val backgroundColor = Brush.verticalGradient(
        colors = listOf(Color(0xFFF7F7F7), Color(0xFFD9F8FF))
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundColor)
            .padding(WindowInsets.systemBars.asPaddingValues())
    ){
        SlideInImage()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 26.dp, end = 26.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircleTimer()
        }
    }
}
@Composable
fun CircleTimer(
    durationMillis: Int = 2 * 60 * 1000 // 2분
) {
    val progress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = durationMillis, easing = LinearEasing)
        )
    }
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
                    color = Color(0xFF4DA6FF),
                    center = center,
                    radius = radius,
                    style = Stroke(backgroundStrokeWidth)
                )
                // 진행 원 (파란색)
                drawArc(
                    color = Color(0xFF4DA6FF),
                    startAngle = -90f, // 위에서 시작
                    sweepAngle = 360f * progress.value,
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
            val remainingSeconds = (1f - progress.value) * durationMillis / 1000f
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
                color = Color(0xFF4DA6FF)
            )
        }
    }

}
@Composable
fun SlideInImage() {
    var visible by remember { mutableStateOf(false) }

    // y축 위치를 상태로 가지고 있다가 애니메이션으로 움직임
    val offsetY by animateDpAsState(
        targetValue = if (visible) 40.dp else 250.dp, // 처음에는 100dp 아래
        animationSpec = tween(durationMillis = 1000),
        label = "imageSlide"
    )

    // 타이머로 애니메이션 트리거
    LaunchedEffect(Unit) {
        delay(500) // 0.5초 뒤 시작
        visible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = painterResource(R.drawable.timer_bird_ready),
            contentDescription = "timer 캐릭터",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = offsetY)
                .width(603.dp)
                .height(573.dp),
            contentScale = ContentScale.Fit
        )
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