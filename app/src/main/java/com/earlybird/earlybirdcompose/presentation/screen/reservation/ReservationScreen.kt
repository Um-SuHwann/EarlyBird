package com.earlybird.earlybirdcompose.presentation.screen.reservation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.core.content.ContextCompat
import com.earlybird.earlybirdcompose.R
import com.earlybird.earlybirdcompose.alarm.AlarmScheduler
import com.earlybird.earlybirdcompose.alarm.AlarmType
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
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit

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

    //권한 요청 부분(나중에 코드를 다른 곳으로 빼서 권한 요청을 하면 좋을 것 같다)
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d("Permission", "알림 권한 허용됨")
        } else {
            Log.d("Permission", "알림 권한 거부됨")
        }
    }
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
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
                    val (hoursLeft, minutesLeft) = calculateRemainingTime(
                        selectedHour,
                        selectedMinute,
                        selectedPa
                    )
                    AlarmScheduler.scheduleAlarm(
                        context = context,
                        alarmType = AlarmType.USER,
                        alarmInfo = alarmInfo,
                    )
                    val message = "${hoursLeft}시간 ${minutesLeft}분 후에\n같이 시작해보자!"
                    //알람 저장 확인
                    Log.d("reservation", alarmInfo.toString())
                    checkPermission(
                        context = context,
                        content = message,
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
fun calculateRemainingTime(hour: Int, minute: Int, amPm: String): Pair<Int, Int> {
    // 12시간 → 24시간 변환
    val hour24 = when {
        amPm == "AM" && hour == 12 -> 0
        amPm == "AM" -> hour
        amPm == "PM" && hour == 12 -> 12
        else -> hour + 12
    }

    val now = LocalDateTime.now()
    var targetTime = now.withHour(hour24).withMinute(minute).withSecond(0)

    // 현재보다 이전 시간인 경우 → 다음날로 예약
    if (targetTime.isBefore(now)) {
        targetTime = targetTime.plusDays(1)
    }

    val secondsUntil = ChronoUnit.SECONDS.between(now, targetTime)
    val roundedMinutes = (secondsUntil + 59) / 60  // 올림 처리
    val hours = (roundedMinutes / 60).toInt()
    val minutes = (roundedMinutes % 60).toInt()

    return hours to minutes
}
@Preview(showBackground = true)
@Composable
fun ReservationScreenPreview(){
    EarlyBirdComposeTheme {
        ReservationScreen()
    }
}