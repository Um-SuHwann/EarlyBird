package com.earlybird.earlybirdcompose.presentation.screen.reservation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdTheme

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
    val itemHeight = 48.dp
    val visibleItemCount = 3
    val listHeight = itemHeight * visibleItemCount

    val repeatedHours = List(100) { hourItems }.flatten()
    val repeatedMinutes = List(100) { minuteItems }.flatten()

    val hourListState = rememberLazyListState(initialFirstVisibleItemIndex = repeatedHours.size / 2)
    val minuteListState = rememberLazyListState(initialFirstVisibleItemIndex = repeatedMinutes.size / 2)
    val paListState = rememberLazyListState(initialFirstVisibleItemIndex = 0)

    val selectedHourIndex by remember { derivedStateOf { hourListState.firstVisibleItemIndex + 1 } }
    val selectedMinuteIndex by remember { derivedStateOf { minuteListState.firstVisibleItemIndex + 1 } }
    val selectedPaIndex by remember { derivedStateOf { paListState.firstVisibleItemIndex } }
    Row(
        modifier = modifier
            .height(listHeight)
            .background(
                color = Color.Transparent,
            )
            .width(220.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 시 (Hour) Picker
        LazyColumn(
            state = hourListState,
            modifier = Modifier
                .width(48.dp)
                .height(listHeight),
            //항목이 정중앙에 딱 맞게 snap하기
            flingBehavior = rememberSnapFlingBehavior(hourListState),
            verticalArrangement = Arrangement.Center
        ) {
            itemsIndexed(repeatedHours) { index, hour ->
                val isSelected = index == selectedHourIndex
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(itemHeight)
                        .clickable { onHourSelected(hour) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = hour,
                        textAlign = TextAlign.Center,
                        color = if (isSelected) EarlyBirdTheme.colors.black else Color(0xFFD3D3D3),
                        fontSize = if (isSelected) 40.sp else 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        // 분 (Minute) Picker
        LazyColumn(
            state = minuteListState,
            modifier = Modifier
                .width(48.dp)
                .height(listHeight),
            //항목이 정중앙에 딱 맞게 snap하기
            flingBehavior = rememberSnapFlingBehavior(minuteListState),
            verticalArrangement = Arrangement.Center
        ) {
            itemsIndexed(repeatedMinutes) { index, minute ->
                val isSelected = index == selectedMinuteIndex
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(itemHeight)
                        .clickable { onMinuteSelected(minute) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = minute,
                        textAlign = TextAlign.Center,
                        color = if (isSelected) EarlyBirdTheme.colors.black else Color(0xFFD3D3D3),
                        fontSize = if (isSelected) 40.sp else 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        // 오전/오후 (AM/PM) Picker
        LazyColumn(
            state = paListState,
            modifier = Modifier
                .width(70.dp)
                .height(listHeight),
            flingBehavior = rememberSnapFlingBehavior(paListState),
            verticalArrangement = Arrangement.Center
        ) {
            item {
                Spacer(modifier = Modifier.height(itemHeight))
            }
            itemsIndexed(paItems) { index, pa ->
                val isSelected = index == selectedPaIndex
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(itemHeight)
                        .clickable { onPaSelected(pa) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = pa,
                        textAlign = TextAlign.Center,
                        color = if (isSelected) EarlyBirdTheme.colors.black else Color(0xFFD3D3D3),
                        fontSize = if (isSelected) 40.sp else 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(itemHeight))
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun WheelPickerPreview() {
    val hourItems = (0..23).map { "%02d".format(it) }
    val minuteItems = (0..59).map { "%02d".format(it) }
    val paItems = listOf("AM", "PM")

    WheelPicker(
        hourItems = hourItems,
        minuteItems = minuteItems,
        paItems = paItems,
        onHourSelected = {},
        onMinuteSelected = {},
        onPaSelected = {}
    )
}