package com.earlybird.earlybirdcompose.presentation.screen.reservation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun WheelPicker(
    modifier: Modifier = Modifier,
    hourItems: List<String>,
    minuteItems: List<String>,
    paItems: List<String>,
    onHourSelected: (String) -> Unit,
    onMinuteSelected: (String) -> Unit,
    onPaSelected: (String) -> Unit,
) {
    val itemHeight = 40.dp
    val visibleItemCount = 3
    val listHeight = itemHeight * visibleItemCount

    val hourListState = rememberLazyListState()
    val minuteListState = rememberLazyListState()
    val paListState = rememberLazyListState()
    Row(
        modifier = modifier
            .height(214.dp)
            .padding(horizontal = 20.dp, vertical = 44.dp)
            .background(
                color = Color.White, // shape_reservation_scroll_layout 대신 적당한 색상 또는 Modifier.clip(Shape)
                shape = RoundedCornerShape(8.dp) // 원래 배경 모양 따라 조절
            )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 시 (Hour) Picker
        LazyColumn(
            state = hourListState,
            modifier = Modifier
                .width(48.dp)
                .height(listHeight),
            flingBehavior = rememberSnapFlingBehavior(hourListState)
        ) {
            itemsIndexed(hourItems) { index, hour ->
                val isSelected = index == hourListState.firstVisibleItemIndex
                Text(
                    text = hour,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(itemHeight)
                        .clickable { onHourSelected(hour) }
                        .padding(vertical = 8.dp),
                    textAlign = TextAlign.Center,
                    color = if (isSelected) Color.Black else Color.Gray,
                    fontSize = if (isSelected) 20.sp else 16.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }

        // 분 (Minute) Picker
        LazyColumn(
            modifier = Modifier
                .width(90.dp)
                .height(150.dp),
            contentPadding = PaddingValues(vertical = 0.dp),
            verticalArrangement = Arrangement.Center,
            flingBehavior = rememberSnapFlingBehavior(lazyListState = rememberLazyListState())
        ) {
            items(minuteItems) { minute ->
                Text(
                    text = minute,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onMinuteSelected(minute) }
                        .padding(vertical = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        // 오전/오후 (AM/PM) Picker
        LazyColumn(
            modifier = Modifier
                .width(90.dp)
                .height(150.dp),
            contentPadding = PaddingValues(vertical = 0.dp),
            verticalArrangement = Arrangement.Center,
            flingBehavior = rememberSnapFlingBehavior(lazyListState = rememberLazyListState())
        ) {
            items(paItems) { pa ->
                Text(
                    text = pa,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onPaSelected(pa) }
                        .padding(vertical = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun WheelPickerPreview(){
    val hourItems = (0..23).map { "%02d".format(it) }
    val minuteItems = (0..59).map { "%02d".format(it) }
    val paItems = listOf("오전", "오후")
    
    WheelPicker(
        hourItems = hourItems,
        minuteItems = minuteItems,
        paItems = paItems,
        onHourSelected = { },
        onMinuteSelected = { },
        onPaSelected = { }
    )
}