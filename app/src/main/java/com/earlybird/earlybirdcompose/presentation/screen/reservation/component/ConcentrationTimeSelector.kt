package com.earlybird.earlybirdcompose.presentation.screen.reservation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.earlybird.earlybirdcompose.R
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdTheme

@Composable
fun ConcentrationTimeSelector() {
    var selectedTime by remember { mutableStateOf<String?>(null) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = EarlyBirdTheme.colors.white)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 74.dp, start = 24.dp, end = 24.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.reservation_motivate_text_icon),
                contentDescription = "동기부여 메시지",
                modifier = Modifier
                    .width(208.dp)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(68.dp))
            Text(
                text = "얼마나 집중하고 싶어?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = EarlyBirdTheme.colors.black,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "선택한 시간만큼 방해받지 않도록 도와줄게!",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF9A9A9A),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TimeSelectGrid(
                onSelect = {
                    selectedTime = it
                },
                selectedTime = selectedTime
            )
        }
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TimeSelectGrid(
    timeOptions: List<String> = listOf("2분", "5분", "15분", "30분", "60분", "90분"),
    selectedTime: String?,
    onSelect: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(timeOptions.size) { index ->
            val time = timeOptions[index]
            val isSelected = time == selectedTime
            Box(
                modifier = Modifier
                    .aspectRatio(1f) // 정사각형
                    .background(
                        color = if (isSelected) EarlyBirdTheme.colors.mainBlue else Color(0xFFF3F4F6),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(1.dp, Color(0xFFCBCBCB), RoundedCornerShape(16.dp))
                    .clickable { onSelect(time) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = time,
                    fontSize = 20.sp,
                    color = if (isSelected) EarlyBirdTheme.colors.white else Color(0xFF838383),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ConcentrationTimeSelectorPreview(){
    ConcentrationTimeSelector()
}