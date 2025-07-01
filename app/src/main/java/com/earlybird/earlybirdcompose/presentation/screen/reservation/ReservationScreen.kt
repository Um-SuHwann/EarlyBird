package com.earlybird.earlybirdcompose.presentation.screen.reservation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.earlybird.earlybirdcompose.R
import com.earlybird.earlybirdcompose.data.model.AlarmInfo
import com.earlybird.earlybirdcompose.presentation.screen.reservation.component.BackTopBar
import com.earlybird.earlybirdcompose.presentation.screen.reservation.component.ConcentrationTimeSelector
import com.earlybird.earlybirdcompose.presentation.screen.reservation.component.RepeatOptionSelector
import com.earlybird.earlybirdcompose.presentation.screen.reservation.component.TodoSpeechBubble
import com.earlybird.earlybirdcompose.presentation.screen.reservation.component.VibrationSelector
import com.earlybird.earlybirdcompose.presentation.screen.reservation.component.WheelPicker
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdComposeTheme
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdTheme
import com.earlybird.earlybirdcompose.util.checkPermission
import java.time.LocalTime

@Composable
fun ReservationScreen(
    onBackClick: () -> Unit = {},
    onSaveAlarm: (AlarmInfo) -> Unit = {}
) {
    //알람 등록할 때 쓰는 정보들
    var todoText by remember { mutableStateOf("") }
    var selectedHour by remember { mutableStateOf(8) }
    var selectedMinute by remember { mutableStateOf(0) }
    var selectedPa by remember { mutableStateOf("AM") }
    var isRepeating by remember { mutableStateOf(true) }
    var isVibrationEnabled by remember { mutableStateOf(true) }
    var focusDuration by remember { mutableIntStateOf(0) }

    //컴포넌트 재사용을 위한 스텝
    var currentStep by remember { mutableStateOf(1) }

    //시간 범위
    val currentTime = LocalTime.now()
    val hourItems = (1..12).map { "%02d".format(it) }
    val minuteItems = (0..59).map { "%02d".format(it) }
    val paItems = listOf("AM", "PM")

    val hour12 = if (currentTime.hour % 12 == 0) 12 else currentTime.hour % 12
    //초기 시간 설정
    val initialHourIndex = hourItems.indexOf("%02d".format(hour12))
    val initialMinuteIndex = minuteItems.indexOf("%02d".format(currentTime.minute))
    val initialPaIndex = if (currentTime.hour >= 12) 1 else 0

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        BackTopBar(onBackClick = onBackClick)
        Spacer(modifier = Modifier.height(25.dp))
        //캐릭터 이미지
        Image(
            painter = painterResource(R.drawable.reservation_bird_icon),
            contentDescription = "새 이미지",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(68.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))
        if(currentStep == 1){
            // 할일 입력 필드
            TodoSpeechBubble(
                text = todoText,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onTextChange = { todoText = it }
            )
            Spacer(modifier = Modifier.height(40.dp))
            // 시간 선택
            WheelPicker(
                modifier = Modifier.align(Alignment.CenterHorizontally),
//                hourItems = hourItems,
//                minuteItems = minuteItems,
                paItems = paItems,
                onHourSelected = { selectedHour = it },
                onMinuteSelected = { selectedMinute = it },
                onPaSelected = { selectedPa = it },
                initialHourIndex = initialHourIndex,
                initialMinuteIndex = initialMinuteIndex,
                initialPaIndex = initialPaIndex
            )
            Spacer(modifier = Modifier.height(48.dp))
            // 반복 설정
            RepeatOptionSelector(
                isRepeating = isRepeating,
                onRepeatChange = { isRepeating = it }
            )
            Spacer(modifier = Modifier.height(20.dp))
            // 진동 설정
            VibrationSelector(
                isVibrationEnabled = isVibrationEnabled,
                onVibrationChange = { isVibrationEnabled = it }
            )
        }
        else{
            ConcentrationTimeSelector(
                focusDuration = focusDuration,
                onFocusDuration = { focusDuration = it }
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        // 저장 버튼
        Button(
            onClick = {
                if(currentStep == 1){
                    currentStep = 2
                }else{
                    val alarmInfo = AlarmInfo(
                        todo = todoText,
                        hour = selectedHour,
                        minute = selectedMinute,
                        amPm = selectedPa,
                        isRepeating = isRepeating,
                        isVibrationEnabled = isVibrationEnabled,
                        focusDurationMinutes = focusDuration
                    )
                    onSaveAlarm(alarmInfo)
                    //알람 저장 확인
                    Log.d("reservation", alarmInfo.toString())
                    checkPermission(
                        context = context,
                        content = "우와! 우리가 해냈다\n다음에도 같이 하자!",
                        buttonContent = "좋아!",
                        durationMillis = focusDuration,
                        isFinished = true
                    )
                }
            },
            modifier = Modifier
                .width(180.dp)
                .height(48.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = EarlyBirdTheme.colors.mainBlue,
                contentColor = EarlyBirdTheme.colors.white
            )

        ) {
            Text(
                text = if(currentStep == 1) "다음" else "시작하기",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReservationScreenPreview(){
    EarlyBirdComposeTheme {
        ReservationScreen()
    }
}