package com.earlybird.earlybirdcompose.presentation.screen.reservation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdTheme

@Composable
fun VibrationSelector(
    isVibrationEnabled: Boolean,
    onVibrationChange: (Boolean) -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = EarlyBirdTheme.colors.white)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "진동",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = EarlyBirdTheme.colors.fontBlack
            )
            Switch(
                checked = isVibrationEnabled,
                onCheckedChange = { onVibrationChange(!isVibrationEnabled) }, // 스위치를 눌렀을 때 저 변수로 결과 값이 전달된다.
                colors = SwitchDefaults.colors(
                    checkedThumbColor = EarlyBirdTheme.colors.white,
                    uncheckedThumbColor = EarlyBirdTheme.colors.white,
                    checkedTrackColor = EarlyBirdTheme.colors.mainBlue,
                    uncheckedTrackColor = Color.LightGray
                )
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun VibrationSelectorPreview(){
    VibrationSelector(isVibrationEnabled = true, onVibrationChange = {})
}