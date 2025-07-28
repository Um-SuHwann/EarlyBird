package com.earlybird.earlybirdcompose.presentation.screen.timer.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdComposeTheme
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdTheme

data class MoodOption(
    val emoji: String,
    val label: String,
    val value: Int
)

@Composable
fun MoodModal(
    isVisible: Boolean,
    onMoodSelected: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    if (isVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.7f))
                .clickable { onDismiss() },
            contentAlignment = Alignment.Center
        ) {
            MoodModalContent(
                onMoodSelected = { mood ->
                    onMoodSelected(mood)
                    onDismiss()
                },
                onCancel = onDismiss,
                modifier = Modifier.clickable { /* 클릭 이벤트 막기 */ }
            )
        }
    }
}

@Composable
private fun MoodModalContent(
    onMoodSelected: (Int) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedMood by remember { mutableStateOf<Int?>(null) }
    
    val moodOptions = listOf(
        MoodOption("😊", "좋음", 5),
        MoodOption("🙂", "보통", 4),
        MoodOption("😐", "그저그런", 3),
        MoodOption("😔", "별로", 2),
        MoodOption("😞", "안좋음", 1)
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "지금 기분이 어떠세요?",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = EarlyBirdTheme.colors.fontBlack,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                moodOptions.forEach { option ->
                    MoodOptionItem(
                        option = option,
                        isSelected = selectedMood == option.value,
                        onClick = { selectedMood = option.value }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onCancel,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "취소",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                
                Button(
                    onClick = { 
                        selectedMood?.let { onMoodSelected(it) }
                    },
                    enabled = selectedMood != null,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EarlyBirdTheme.colors.mainBlue,
                        contentColor = Color.White,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.White
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "확인",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun MoodOptionItem(
    option: MoodOption,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) EarlyBirdTheme.colors.mainBlue.copy(alpha = 0.1f) else Color.Transparent,
        border = if (isSelected) 
            androidx.compose.foundation.BorderStroke(2.dp, EarlyBirdTheme.colors.mainBlue) 
        else 
            androidx.compose.foundation.BorderStroke(1.dp, Color.Gray.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = option.emoji,
                fontSize = 24.sp,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Text(
                text = option.label,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = EarlyBirdTheme.colors.fontBlack
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoodModalPreview() {
    EarlyBirdComposeTheme {
        MoodModal(
            isVisible = true,
            onMoodSelected = { },
            onDismiss = { }
        )
    }
}