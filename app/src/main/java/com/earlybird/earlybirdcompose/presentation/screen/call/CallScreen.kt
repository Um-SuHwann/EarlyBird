package com.earlybird.earlybirdcompose.presentation.screen.call

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.earlybird.earlybirdcompose.R
import com.earlybird.earlybirdcompose.presentation.screen.call.componenet.SwipeButton
import com.earlybird.earlybirdcompose.presentation.screen.call.componenet.TodoTaskCard
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdComposeTheme
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdTheme
import kotlinx.coroutines.delay

@Composable
fun CallScreen(
    callerName: String = "얼리버드",
    todoTask: String = "",
    durationMillis: Int,
    onStartCall: () -> Unit = {}
) {
    var isCallStarted by remember { mutableStateOf(false) }
    var countdown by remember { mutableIntStateOf(5) }
    var isCountdownFinished by remember { mutableStateOf(false) }
    var showCountdownText by remember { mutableStateOf(false) }

    LaunchedEffect(isCallStarted) {
        if (isCallStarted) {
            delay(2000) // 이미지 바뀌고 2초 대기
            showCountdownText = true
            for (i in 5 downTo 1) {
                countdown = i
                delay(1000)
            }
            isCountdownFinished = true
            onStartCall()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .background(Color(0xFF101010)),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(42.dp))
            Text(
                text = callerName,
                color = EarlyBirdTheme.colors.white,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            TodoTaskCard(
                modifier = Modifier,
                todoTask = todoTask
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 224.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            when {
                !isCallStarted -> {
                    Image(
                        modifier = Modifier.width(218.dp),
                        painter = painterResource(R.drawable.call_bird_calling_icon),
                        contentScale = ContentScale.Fit,
                        contentDescription = "새 이미지"
                    )
                }
                !showCountdownText -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier.width(218.dp),
                            painter = painterResource(R.drawable.call_bird_called_icon),
                            contentScale = ContentScale.Fit,
                            contentDescription = "새 이미지"
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "전화받아줘서 고마워!\n이미 절반은 성공한거야",
                            fontSize = 25.sp,
                            color = EarlyBirdTheme.colors.white,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
        if (showCountdownText && !isCountdownFinished){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(
                    text = "$countdown",
                    fontSize = 90.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(85.dp)) // 숫자와 텍스트 사이 간격
                Text(
                    text = "${countdown}초 뒤에 같이 시작하러 가는거야!\n설마 안하진 않겠지?",
                    fontSize = 25.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
        }
        // 하단 스와이프 버튼 될 예정
        if (!isCallStarted) {
            SwipeButton(
                onStartCall = {
                    isCallStarted = true
                },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CallScreenPreview() {
    EarlyBirdComposeTheme {
        CallScreen(durationMillis = 2*60*1000)
    }
}