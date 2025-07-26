package com.earlybird.earlybirdcompose.presentation.screen.reservation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Button
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.earlybird.earlybirdcompose.presentation.screen.reservation.component.FeatureSelector
import com.earlybird.earlybirdcompose.presentation.screen.reservation.component.FeatureState
import com.earlybird.earlybirdcompose.presentation.screen.reservation.component.Mood
import com.earlybird.earlybirdcompose.presentation.screen.reservation.component.MoodSelector
import com.earlybird.earlybirdcompose.presentation.screen.reservation.component.RepeatOptionSelector
import com.earlybird.earlybirdcompose.presentation.screen.reservation.component.TodoSpeechBubble
import com.earlybird.earlybirdcompose.presentation.screen.reservation.component.TopSpeechBubble
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

    //새로운 상태들
    var selectedMood by remember { mutableStateOf<Mood?>(null) }
    var featureState by remember { mutableStateOf(FeatureState()) }

    //컴포넌트 재사용을 위한 스텝 (1: 기분 선택, 2: 할일 입력 및 기능 선택)
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

    //새가 말하는 말풍선 내용
    val speechText = if(currentStep == 2 && selectedMood != null){
        when(selectedMood){
            Mood.BAD -> "Though day... Try just one small thing."
            Mood.NORMAL -> "Middle mood! still room to move."
            Mood.GOOD -> "Energy is here! Use it your way"
            else -> "Let's get started!"
        }
    }else{
        ""
    }
    
    // 디버깅용 로그
    Log.d("ReservationScreen", "currentStep: $currentStep, selectedMood: $selectedMood, speechText: '$speechText'")

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
        Spacer(modifier = Modifier.height(16.dp))
        TopSpeechBubble(
            modifier = Modifier.padding(horizontal = 12.dp),
            leftImageRes = R.drawable.reservation_bird_icon,
            speechText = speechText
        )
        Spacer(modifier = Modifier.height(110.dp))
        Text(
            text = if(currentStep == 1) "How are you feeling today?" else "Set your To-do list",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = EarlyBirdTheme.colors.fontBlack,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = if(currentStep == 1) "Before we start,\nlet's check in with your emotions" else "What would you like to do?",
            style = TextStyle(lineHeight = 20.sp),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = EarlyBirdTheme.colors.fontBlack,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center
        )
        if(currentStep == 1){
            // 첫 번째 단계: 기분 선택
            Spacer(modifier = Modifier.height(40.dp))
            
            MoodSelector(
                selectedMood = selectedMood,
                onMoodSelected = { selectedMood = it },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        else{
            // 두 번째 단계: 할 일 입력 및 기능 선택
            Spacer(modifier = Modifier.height(16.dp))

            FeatureSelector(
                featureState = featureState,
                onFeatureChange = { featureState = it },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        Spacer(modifier = Modifier.height(132.dp))

        // 저장 버튼
        Button(
            onClick = {
                if(currentStep == 1){
                    if(selectedMood != null) {
                        currentStep = 2
                        Log.d("reservation", "Selected mood: $selectedMood")
                    }
                }else{
                    Log.d("reservation", "Selected mood: $selectedMood")
                    Log.d("reservation", "Selected features: $featureState")

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
                .width(216.dp)
                .height(40.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = EarlyBirdTheme.colors.mainBlue,
                contentColor = EarlyBirdTheme.colors.white
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 3.dp
            )

        ) {
            Text(
                text = if(currentStep == 1) "Continue" else "Done",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
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