package com.earlybird.earlybirdcompose.presentation.screen.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.earlybird.earlybirdcompose.R
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdComposeTheme
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
fun NextCallReminderComponent(
    nextCallTime: Long?, // timestamp in milliseconds
    todoTitle: String?,
    modifier: Modifier = Modifier
) {
    if (nextCallTime == null || todoTitle == null) {
        return // 알람 설정된 할일이 없으면 컴포넌트를 표시하지 않음
    }
    
    val currentTime = System.currentTimeMillis()
    val timeRemaining = nextCallTime - currentTime
    
    // 이미 지난 시간이면 표시하지 않음
    if (timeRemaining <= 0) {
        return
    }
    
    val timeText = formatTimeRemaining(timeRemaining)
    val callTimeText = formatCallTime(nextCallTime)
    
    Box(
        modifier = modifier
            .background(
                color = Color(0xFFF8F9FA),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 전화 아이콘
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = EarlyBirdTheme.colors.mainBlue.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.reservation_bird_basic), // TODO: 전화 아이콘으로 변경
                    contentDescription = "Next call",
                    tint = EarlyBirdTheme.colors.mainBlue,
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Next Call",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF6B7280)
                )
                
                Text(
                    text = timeText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = EarlyBirdTheme.colors.fontBlack
                )
                
                Text(
                    text = "$callTimeText • $todoTitle",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF9CA3AF)
                )
            }
        }
    }
}

private fun formatTimeRemaining(timeRemaining: Long): String {
    val hours = TimeUnit.MILLISECONDS.toHours(timeRemaining)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(timeRemaining) % 60
    
    return when {
        hours > 0 -> "${hours}h ${minutes}m left"
        minutes > 0 -> "${minutes}m left"
        else -> "Less than 1m left"
    }
}

private fun formatCallTime(timestamp: Long): String {
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatter.format(Date(timestamp))
}

@Preview(showBackground = true)
@Composable
fun NextCallReminderComponentPreview() {
    EarlyBirdComposeTheme {
        NextCallReminderComponent(
            nextCallTime = System.currentTimeMillis() + (2 * 60 * 60 * 1000), // 2시간 후
            todoTitle = "운동하기"
        )
    }
}