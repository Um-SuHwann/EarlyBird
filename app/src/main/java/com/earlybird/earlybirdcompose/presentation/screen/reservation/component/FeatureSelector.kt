package com.earlybird.earlybirdcompose.presentation.screen.reservation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdTheme

data class FeatureState(
    val isTimerEnabled: Boolean = false,
    val isCallEnabled: Boolean = false,
    val selectedTimerMinutes: Int = 2,
    val selectedCallHour: Int = 8,
    val selectedCallMinute: Int = 0,
    val selectedCallAmPm: String = "AM"
)

@Composable
fun FeatureSelector(
    featureState: FeatureState,
    onFeatureChange: (FeatureState) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
//        // Timer 기능
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(
//                text = "Need focus timer",
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Medium,
//                color = EarlyBirdTheme.colors.fontBlack
//            )
//            Switch(
//                checked = featureState.isTimerEnabled,
//                onCheckedChange = {
//                    onFeatureChange(featureState.copy(isTimerEnabled = it))
//                },
//                colors = SwitchDefaults.colors(
//                    checkedThumbColor = EarlyBirdTheme.colors.white,
//                    checkedTrackColor = EarlyBirdTheme.colors.mainBlue,
//                    uncheckedThumbColor = EarlyBirdTheme.colors.white,
//                    uncheckedTrackColor = Color(0xFF838383)
//                )
//            )
//        }
        
//        // Timer 시간 선택 (Timer가 켜져 있을 때만 표시)
//        if (featureState.isTimerEnabled) {
//            TimerDurationSelector(
//                selectedMinutes = featureState.selectedTimerMinutes,
//                onMinutesSelected = { minutes ->
//                    onFeatureChange(featureState.copy(selectedTimerMinutes = minutes))
//                }
//            )
//        }
        
//        //중간선
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(1.dp)
//                .background(Color(0xFFC9C9C9))
//        )
        // Call 기능
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Need phone call",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = EarlyBirdTheme.colors.fontBlack
            )
            Switch(
                checked = featureState.isCallEnabled,
                onCheckedChange = { 
                    onFeatureChange(featureState.copy(isCallEnabled = it))
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = EarlyBirdTheme.colors.white,
                    checkedTrackColor = EarlyBirdTheme.colors.mainBlue,
                    uncheckedThumbColor = EarlyBirdTheme.colors.white,
                    uncheckedTrackColor = Color(0xFF838383)
                )
            )
        }
        
        // Call 시간 선택 (Call이 켜져 있을 때만 표시)
        if (featureState.isCallEnabled) {
            CallTimeSelector(
                selectedHour = featureState.selectedCallHour,
                selectedMinute = featureState.selectedCallMinute,
                selectedAmPm = featureState.selectedCallAmPm,
                onTimeSelected = { hour, minute, amPm ->
                    onFeatureChange(featureState.copy(
                        selectedCallHour = hour,
                        selectedCallMinute = minute,
                        selectedCallAmPm = amPm
                    ))
                }
            )
        }
    }
}

//@Composable
//fun TimerDurationSelector(
//    selectedMinutes: Int,
//    onMinutesSelected: (Int) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    val durations = listOf(2, 5, 10)
//
//    Row(
//        modifier = modifier
//            .fillMaxWidth(),
//        horizontalArrangement = Arrangement.spacedBy(18.dp, Alignment.CenterHorizontally),
//    ) {
//        durations.forEach { duration ->
//            val isSelected = selectedMinutes == duration
//            Box(
//                modifier = Modifier
//                    .clip(RoundedCornerShape(4.dp))
//                    .width(80.dp).height(30.dp)
//                    .background(
//                        if (isSelected) EarlyBirdTheme.colors.mainBlue
//                        else EarlyBirdTheme.colors.white
//                    )
//                    .border(
//                        1.dp,
//                        if (isSelected) EarlyBirdTheme.colors.mainBlue
//                        else Color(0xFFC1C1C1),
//                        RoundedCornerShape(4.dp)
//                    )
//                    .clickable { onMinutesSelected(duration) },
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = "${duration}min",
//                    fontSize = 12.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = if (isSelected) EarlyBirdTheme.colors.white
//                           else EarlyBirdTheme.colors.fontBlack
//                )
//            }
//        }
//    }
//}

@Composable
fun CallTimeSelector(
    selectedHour: Int,
    selectedMinute: Int,
    selectedAmPm: String,
    onTimeSelected: (Int, Int, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showTimePicker by remember { mutableStateOf(false) }
    
    Row(
        modifier = modifier.padding(start = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 시간 선택
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(EarlyBirdTheme.colors.white)
                .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                .clickable { showTimePicker = true }
                .padding(horizontal = 12.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = String.format("%02d:%02d %s", selectedHour, selectedMinute, selectedAmPm),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = EarlyBirdTheme.colors.fontBlack
            )
        }
        
        Text(
            text = "Tap to change time",
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF999999)
        )
    }
    
    if (showTimePicker) {
        TimePickerDialog(
            selectedHour = selectedHour,
            selectedMinute = selectedMinute,
            selectedAmPm = selectedAmPm,
            onTimeSelected = onTimeSelected,
            onDismiss = { showTimePicker = false }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FeatureSelectorPreview() {
    FeatureSelector(
        featureState = FeatureState(
            isTimerEnabled = false,
            isCallEnabled = true,
            selectedTimerMinutes = 25,
            selectedCallHour = 8,
            selectedCallMinute = 30,
            selectedCallAmPm = "AM"
        ),
        onFeatureChange = {}
    )
}