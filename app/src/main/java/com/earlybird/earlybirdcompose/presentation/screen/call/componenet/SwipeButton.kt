//package com.earlybird.earlybirdcompose.presentation.screen.call.componenet
//
//import androidx.compose.animation.core.Animatable
//import androidx.compose.foundation.background
//import androidx.compose.foundation.gestures.Orientation
//import androidx.compose.foundation.gestures.detectDragGestures
//import androidx.compose.foundation.gestures.draggable
//import androidx.compose.foundation.gestures.rememberDraggableState
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Call
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.input.pointer.pointerInput
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.IntOffset
//import androidx.compose.ui.unit.dp
//import com.earlybird.earlybirdcompose.presentation.screen.call.CallScreen
//import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdComposeTheme
//import kotlinx.coroutines.launch
//
//@Composable
//fun SwipeButton(
//    onAnswer: () -> Unit,
//    onDecline: () -> Unit
//) {
//    val maxSwipeDistance = 260f
//    val swipeOffset = remember { Animatable(0f) }
//
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(64.dp)
//            .background(Color.LightGray, RoundedCornerShape(32.dp))
//            .pointerInput(Unit) {
//                detectDragGestures(
//                    onDragEnd = {
//                        if (swipeOffset.value > maxSwipeDistance * 0.7f) {
//                            onSwiped()
//                        }
//                        // 자동 복귀
//                        launch {
//                            swipeOffset.animateTo(0f)
//                        }
//                    }
//                ) { change, dragAmount ->
//                    change.consume()
//                    val newOffset = (swipeOffset.value + dragAmount.x).coerceIn(0f, maxSwipeDistance)
//                    launch {
//                        swipeOffset.snapTo(newOffset)
//                    }
//                }
//            },
//        contentAlignment = Alignment.CenterStart
//    ) {
//        Box(
//            modifier = Modifier
//                .offset { IntOffset(swipeOffset.value.roundToInt(), 0) }
//                .padding(8.dp)
//                .size(48.dp)
//                .background(Color.DarkGray, CircleShape),
//            contentAlignment = Alignment.Center
//        ) {
//            Icon(
//                imageVector = Icons.Default.Phone,
//                contentDescription = "Swipe",
//                tint = Color.White
//            )
//        }
//
//        Text(
//            text = "→ 오른쪽으로 스와이프해서 받기",
//            color = Color.Gray,
//            modifier = Modifier
//                .align(Alignment.Center)
//                .padding(start = 16.dp)
//        )
//    }
//}
//@Preview(showBackground = true)
//@Composable
//fun SwipeButtonPreview() {
//    SwipeButton(
//        onAnswer = {},
//        onDecline = {}
//    )
//}