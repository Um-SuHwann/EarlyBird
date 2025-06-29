package com.earlybird.earlybirdcompose.presentation.screen.reservation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.earlybird.earlybirdcompose.R
import com.earlybird.earlybirdcompose.presentation.screen.reservation.component.BackTopBar
import com.earlybird.earlybirdcompose.presentation.screen.reservation.component.RepeatOptionSelector
import com.earlybird.earlybirdcompose.presentation.screen.reservation.component.TodoSpeechBubble
import com.earlybird.earlybirdcompose.presentation.screen.reservation.component.VibrationSelector
import com.earlybird.earlybirdcompose.presentation.screen.reservation.component.WheelPicker
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdComposeTheme
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdTheme
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationScreen() {
    var todoText by remember { mutableStateOf("") }
    var isRepeating by remember { mutableStateOf(false) }
    var isVibrationEnabled by remember { mutableStateOf(true) }
    
    val currentTime = LocalTime.now()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.hour,
        initialMinute = currentTime.minute
    )
    
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        BackTopBar()
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
        // 할일 입력 필드
        TodoSpeechBubble(
            text = todoText,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(40.dp))
        // 시간 선택
        val hourItems = (0..23).map { "%02d".format(it) }
        val minuteItems = (0..59).map { "%02d".format(it) }
        val paItems = listOf("AM", "PM")

        WheelPicker(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            hourItems = hourItems,
            minuteItems = minuteItems,
            paItems = paItems,
            onHourSelected = { },
            onMinuteSelected = { },
            onPaSelected = { }
        )
        Spacer(modifier = Modifier.height(48.dp))
        // 반복 설정
        RepeatOptionSelector()
        Spacer(modifier = Modifier.height(20.dp))
        // 진동 설정
        VibrationSelector()
        Spacer(modifier = Modifier.height(30.dp))
        
        // 저장 버튼
        Button(
            onClick = {
                // TODO: 저장 로직 구현
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
                text = "다음",
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