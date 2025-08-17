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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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

// 상수 정의
private object CallScreenConstants {
    const val TRANSITION_DELAY = 2000L
    const val COUNTDOWN_START = 5
    const val COUNTDOWN_STEP_DELAY = 1000L
}

// Call state enum
private enum class CallState {
    WAITING,     // Waiting for call
    ANSWERED,    // Call answered (showing thanks message)
    COUNTDOWN,   // Countdown in progress
    FINISHED     // Countdown completed
}

@Composable
fun CallScreen(
    callerName: String = "EarlyBird",
    todoTask: String = "",
    durationMillis: Int,
    onStartCall: () -> Unit = {},
    onNotNow: () -> Unit
) {
    var callState by remember { mutableStateOf(CallState.WAITING) }
    var countdown by remember { mutableIntStateOf(CallScreenConstants.COUNTDOWN_START) }

    LaunchedEffect(callState) {
        when (callState) {
            CallState.ANSWERED -> {
                delay(CallScreenConstants.TRANSITION_DELAY)
                callState = CallState.COUNTDOWN
            }
            CallState.COUNTDOWN -> {
                for (i in CallScreenConstants.COUNTDOWN_START downTo 1) {
                    countdown = i
                    delay(CallScreenConstants.COUNTDOWN_STEP_DELAY)
                }
                callState = CallState.FINISHED
                onStartCall()
            }
            else -> { /* No action needed */ }
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
                todoTask = todoTask,
                basic = "Ready for this? Let's go"
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 224.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            when (callState) {
                CallState.WAITING -> {
                    Image(
                        modifier = Modifier.width(218.dp),
                        painter = painterResource(R.drawable.call_bird_calling_icon),
                        contentScale = ContentScale.Fit,
                        contentDescription = "Calling bird icon"
                    )
                }
                CallState.ANSWERED -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier.width(218.dp),
                            painter = painterResource(R.drawable.call_bird_called_icon),
                            contentScale = ContentScale.Fit,
                            contentDescription = "Bird answered call icon"
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            text = "Thanks for picking up! \uD83D\uDC23\nYou're already halfway there\uD83D\uDC4F",
                            fontSize = 23.sp,
                            color = EarlyBirdTheme.colors.white,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                else -> { /* 카운트다운과 완료 상태에서는 이미지 숨김 */ }
            }
        }
        if (callState == CallState.COUNTDOWN) {
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
                    text = "Starting in ${countdown} sec\n" + "don’t even think about\nskipping \uD83D\uDE0F",
                    fontSize = 23.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
        }
        // 하단 스와이프 버튼 될 예정
        if (callState == CallState.WAITING) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 33.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SwipeButton(
                    onStartCall = {
                        callState = CallState.ANSWERED
                    },
                    modifier = Modifier
                )
                Spacer(Modifier.height(28.dp))
                Button(
                    onClick = onNotNow,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EarlyBirdTheme.colors.white,
                        contentColor = EarlyBirdTheme.colors.black
                    ),
                    modifier = Modifier
                ) {
                    Text(
                        text = "Not now",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CallScreenPreview() {
    EarlyBirdComposeTheme {
        CallScreen(
            durationMillis = 2*60*1000,
            onNotNow = {}
        )
    }
}