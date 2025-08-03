package com.earlybird.earlybirdcompose.presentation.screen.main.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.earlybird.earlybirdcompose.R
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdComposeTheme
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdTheme

data class TodoItem(
    val id: Int,
    val text: String,
    val reservedTime: String? = null, // 예약 시간 (예: "09:00 AM")
    val timerDuration: String? = null, // 타이머 시간 (예: "2min")
    val hasTimer: Boolean = false, // 타이머 설정 여부
    val hasCall: Boolean = false // 전화 기능 여부
)

@Composable
fun TodoListComponent(
    todoItems: List<TodoItem>,
    onStartClick: (TodoItem) -> Unit,
    modifier: Modifier = Modifier
) {
    // 전화 예약 todo와 일반 todo 분리
    val callTodos = todoItems.filter { it.hasCall && it.reservedTime != null }
        .sortedBy { it.reservedTime } // 시간 순서대로 정렬
    val regularTodos = todoItems.filter { !it.hasCall || it.reservedTime == null }
    
    Surface(
        modifier = modifier
            .fillMaxSize()
            .height(300.dp),
        color = EarlyBirdTheme.colors.white,
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 32.dp)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(28.dp)
            ) {
                // 전화 예약 todo 섹션
                if (callTodos.isNotEmpty()) {

                    items(callTodos) { todoItem ->
                        TodoItemRow(
                            todoItem = todoItem,
                            onStartClick = { onStartClick(todoItem) }
                        )
                    }
                    
                    // 구분선
                    if (regularTodos.isNotEmpty()) {
                        item {
                            HorizontalDivider(
                                modifier = Modifier,
                                thickness = 1.dp,
                                color = Color(0xFFC5C5C5))
                        }
                    }
                }
                // 일반 todo 섹션
                if (regularTodos.isNotEmpty()) {
                    items(regularTodos) { todoItem ->
                        TodoItemRow(
                            todoItem = todoItem,
                            onStartClick = { onStartClick(todoItem) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TodoItemRow(
    todoItem: TodoItem,
    onStartClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = EarlyBirdTheme.colors.white,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke((1.5).dp,Color(0xFFD2D2D2))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = todoItem.text,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0E0E0E)
                )
                // 예약 시간, 타이머 시간, 아이콘들을 표시하는 Row
                if (todoItem.reservedTime != null || todoItem.timerDuration != null || todoItem.hasTimer || todoItem.hasCall) {
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // 예약 시간 표시
                        todoItem.reservedTime?.let { time ->
                            Text(
                                text = time,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = EarlyBirdTheme.colors.fontBlack
                            )
                        }
                        
                        // 타이머 시간 표시
                        todoItem.timerDuration?.let { duration ->
                            Text(
                                text = duration,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = EarlyBirdTheme.colors.fontBlack
                            )
                        }
                        
                        // 타이머 아이콘
                        if (todoItem.hasTimer) {
                            Image(
                                painter = painterResource(id = R.drawable.main_timer_icon), // 임시 아이콘, 나중에 타이머 아이콘으로 변경
                                contentDescription = "Timer",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        
                        // 전화 아이콘
                        if (todoItem.hasCall) {
                            Image(
                                painter = painterResource(id = R.drawable.main_phone_icon), // 임시 아이콘, 나중에 전화 아이콘으로 변경
                                contentDescription = "Call",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = onStartClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = EarlyBirdTheme.colors.mainBlue,
                    contentColor = EarlyBirdTheme.colors.white,
                ),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .size(56.dp)) {
                Text(
                    text = "Start",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoListComponentPreview() {
    EarlyBirdComposeTheme {
        TodoListComponent(
            todoItems = listOf(
                // 전화 예약 todo (시간 순서대로 표시됨)
                TodoItem(1, "Call doctor", reservedTime = "09:00 AM", hasCall = true),
                TodoItem(2, "Call mom", reservedTime = "02:00 PM", hasCall = true),
                TodoItem(3, "Meeting with client", reservedTime = "04:30 PM", hasCall = true),
                
                // 일반 todo
                TodoItem(4, "Read for 30 minutes", timerDuration = "30 min", hasTimer = true),
                TodoItem(5, "Exercise for 20 minutes", timerDuration = "20 min", hasTimer = true),
                TodoItem(6, "Write journal entry"),
                TodoItem(7, "Grocery shopping")
            ),
            onStartClick = { /* Handle start click */ }
        )
    }
}