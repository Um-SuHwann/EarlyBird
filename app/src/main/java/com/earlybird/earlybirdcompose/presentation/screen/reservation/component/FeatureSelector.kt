package com.earlybird.earlybirdcompose.presentation.screen.reservation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.earlybird.earlybirdcompose.R

data class FeatureState(
    val isTimerEnabled: Boolean = false,
    val isCallEnabled: Boolean = false
)

@Composable
fun FeatureSelector(
    featureState: FeatureState,
    onFeatureChange: (FeatureState) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MoodButton(
                imageRes = R.drawable.reservation_bird_icon, // TODO: 타이머 이미지로 변경
                selectedImageRes = R.drawable.reservation_bird_icon, // TODO: 선택된 타이머 이미지로 변경
                text = "Timer",
                isSelected = featureState.isTimerEnabled,
                selectedColor = Color(0xFF4CAF50), // 초록색
                onClick = { 
                    onFeatureChange(featureState.copy(isTimerEnabled = !featureState.isTimerEnabled))
                }
            )
            
            MoodButton(
                imageRes = R.drawable.reservation_bird_icon, // TODO: 전화 이미지로 변경
                selectedImageRes = R.drawable.reservation_bird_icon, // TODO: 선택된 전화 이미지로 변경
                text = "Call",
                isSelected = featureState.isCallEnabled,
                selectedColor = Color(0xFF2196F3), // 파란색
                onClick = { 
                    onFeatureChange(featureState.copy(isCallEnabled = !featureState.isCallEnabled))
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FeatureSelectorPreview() {
    FeatureSelector(
        featureState = FeatureState(isTimerEnabled = true, isCallEnabled = false),
        onFeatureChange = {}
    )
}