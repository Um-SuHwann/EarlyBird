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
    val selectedCallHour: Int = getCurrentTimeData().hour,
    val selectedCallMinute: Int = getCurrentTimeData().minute,
    val selectedCallAmPm: String = getCurrentTimeData().amPm
)

private data class TimeData(val hour: Int, val minute: Int, val amPm: String)

private fun getCurrentTimeData(): TimeData {
    val now = java.time.LocalTime.now()
    val currentMinute = now.minute
    
    // 15분 단위로 올림
    val roundedMinute = ((currentMinute + 14) / 15) * 15
    
    // 60분을 넘으면 시간 조정
    val adjustedTime = if (roundedMinute >= 60) {
        now.plusHours(1).withMinute(0)
    } else {
        now.withMinute(roundedMinute)
    }
    
    val hour24 = adjustedTime.hour
    val hour12 = if (hour24 == 0) 12
    else if (hour24 > 12) hour24 - 12
    else hour24
    
    val amPm = if (hour24 < 12) "AM" else "PM"
    
    return TimeData(hour12, adjustedTime.minute, amPm)
}

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