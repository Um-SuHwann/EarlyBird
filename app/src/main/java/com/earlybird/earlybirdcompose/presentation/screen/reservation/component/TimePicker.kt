package com.earlybird.earlybirdcompose.presentation.screen.reservation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdTheme

@Composable
fun TimePickerDialog(
    selectedHour: Int,
    selectedMinute: Int,
    selectedAmPm: String,
    onTimeSelected: (Int, Int, String) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(EarlyBirdTheme.colors.white)
                .padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Select Call Time",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = EarlyBirdTheme.colors.fontBlack
                )
                
                var currentHour by remember { mutableStateOf(selectedHour) }
                var currentMinute by remember { mutableStateOf(selectedMinute) }
                var currentAmPm by remember { mutableStateOf(selectedAmPm) }
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Hour selector
                    NumberSelector(
                        value = currentHour,
                        range = 1..12,
                        onValueChange = { currentHour = it },
                        formatter = { "%02d".format(it) }
                    )
                    
                    Text(":", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    
                    // Minute selector (15분 단위)
                    MinuteSelector(
                        value = currentMinute,
                        onValueChange = { currentMinute = it }
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    // AM/PM selector
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        listOf("AM", "PM").forEach { period ->
                            Box(
                                modifier = Modifier
                                    .width(48.dp)
                                    .height(32.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        if (currentAmPm == period) EarlyBirdTheme.colors.mainBlue
                                        else EarlyBirdTheme.colors.white
                                    )
                                    .border(
                                        1.dp,
                                        if (currentAmPm == period) EarlyBirdTheme.colors.mainBlue
                                        else Color(0xFFE0E0E0),
                                        RoundedCornerShape(8.dp)
                                    )
                                    .clickable { currentAmPm = period },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = period,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = if (currentAmPm == period) EarlyBirdTheme.colors.white
                                           else EarlyBirdTheme.colors.fontBlack
                                )
                            }
                        }
                    }
                }
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Cancel button
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFF5F5F5))
                            .clickable { onDismiss() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Cancel",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = EarlyBirdTheme.colors.fontBlack
                        )
                    }
                    
                    // OK button
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(EarlyBirdTheme.colors.mainBlue)
                            .clickable { 
                                onTimeSelected(currentHour, currentMinute, currentAmPm)
                                onDismiss()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "OK",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = EarlyBirdTheme.colors.white
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MinuteSelector(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val minuteOptions = listOf(0, 15, 30, 45)
    val currentIndex = minuteOptions.indexOf(value).takeIf { it >= 0 } ?: 0
    
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Up button
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFF5F5F5))
                .clickable { 
                    val newIndex = (currentIndex + 1) % minuteOptions.size
                    onValueChange(minuteOptions[newIndex])
                },
            contentAlignment = Alignment.Center
        ) {
            Text("▲", fontSize = 12.sp, color = EarlyBirdTheme.colors.fontBlack)
        }
        
        // Current value
        Box(
            modifier = Modifier
                .width(48.dp)
                .height(40.dp)
                .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "%02d".format(minuteOptions[currentIndex]),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = EarlyBirdTheme.colors.fontBlack
            )
        }
        
        // Down button
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFF5F5F5))
                .clickable { 
                    val newIndex = (currentIndex - 1 + minuteOptions.size) % minuteOptions.size
                    onValueChange(minuteOptions[newIndex])
                },
            contentAlignment = Alignment.Center
        ) {
            Text("▼", fontSize = 12.sp, color = EarlyBirdTheme.colors.fontBlack)
        }
    }
}

@Composable
fun NumberSelector(
    value: Int,
    range: IntRange,
    onValueChange: (Int) -> Unit,
    formatter: (Int) -> String = { it.toString() },
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Up button
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFF5F5F5))
                .clickable { 
                    val newValue = if (value == range.last) range.first else value + 1
                    onValueChange(newValue)
                },
            contentAlignment = Alignment.Center
        ) {
            Text("▲", fontSize = 12.sp, color = EarlyBirdTheme.colors.fontBlack)
        }
        
        // Current value
        Box(
            modifier = Modifier
                .width(48.dp)
                .height(40.dp)
                .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = formatter(value),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = EarlyBirdTheme.colors.fontBlack
            )
        }
        
        // Down button
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFF5F5F5))
                .clickable { 
                    val newValue = if (value == range.first) range.last else value - 1
                    onValueChange(newValue)
                },
            contentAlignment = Alignment.Center
        ) {
            Text("▼", fontSize = 12.sp, color = EarlyBirdTheme.colors.fontBlack)
        }
    }
}