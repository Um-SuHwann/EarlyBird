package com.earlybird.earlybirdcompose.presentation.screen.timer.component

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.earlybird.earlybirdcompose.R
import kotlinx.coroutines.delay

//하단 이미지 (올라오는 애니메이션 추가된 이미지)
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