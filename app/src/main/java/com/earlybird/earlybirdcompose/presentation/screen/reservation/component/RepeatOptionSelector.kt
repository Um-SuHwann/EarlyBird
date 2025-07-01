package com.earlybird.earlybirdcompose.presentation.screen.reservation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.earlybird.earlybirdcompose.R
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdTheme

@Composable
fun RepeatOptionSelector(
    isRepeating: Boolean = true,
    onRepeatChange: (Boolean) -> Unit = {}
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = EarlyBirdTheme.colors.white)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 32.dp)
        ) {
            Text(
                text = "반복 설정",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = EarlyBirdTheme.colors.fontBlack,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 20.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .background(
                        color = Color.Transparent, // shape_reservation_scroll_layout 대신 적당한 색상 또는 Modifier.clip(Shape)
                    )
                    .fillMaxWidth()
                    .padding(horizontal = 47.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RepeatOptionButton(
                    text = "반복 알람",
                    painter = painterResource(R.drawable.reservation_repeat_icon),
                    selected = isRepeating,
                    onClick = { onRepeatChange(true) }
                )

                RepeatOptionButton(
                    text = "한 번 알람",
                    painter = painterResource(R.drawable.reservation_once_icon),
                    selected = !isRepeating,
                    onClick = { onRepeatChange(false) }
                )
            }
        }
    }
}
@Composable
fun RepeatOptionButton(
    text: String,
    painter: Painter,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(120.dp)
            .height(65.dp)
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(10)) // pill-shaped
            .background(if (selected) EarlyBirdTheme.colors.mainBlue else Color(0xFFF3F4F6))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painter,
                contentDescription = "반복 이미지",
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = text,
                color = if (selected) EarlyBirdTheme.colors.white else EarlyBirdTheme.colors.fontBlack,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun RepeatOptionSelectorPreview(){
    RepeatOptionSelector()
}