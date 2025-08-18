package com.earlybird.earlybirdcompose.presentation.screen.main.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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

enum class TodoStatus {
    NOT_STARTED,  // 시작 전 - "Start" 버튼
    IN_PROGRESS,  // 진행 중 - "Done" 버튼  
    COMPLETED     // 완료 - 완료 아이콘
}

data class TodoItem(
    val id: Int,
    val text: String, //할일
    val reservedTime: String? = null, // 예약 시간 (예: "09:00 AM")
    val timerDuration: String? = null, // 타이머 시간 (예: "2min")
    val hasTimer: Boolean = false, // 타이머 설정 여부
    val hasCall: Boolean = false, // 전화 기능 여부
    val status: TodoStatus = TodoStatus.NOT_STARTED // 할일 상태
)

// 시간을 24시간 형식의 분 단위로 변환하는 함수
private fun parseTimeToMinutes(timeString: String): Int {
    if (timeString.isBlank()) return 0
    
    try {
        // "09:00 AM" 또는 "2:30 PM" 형식 파싱
        val parts = timeString.trim().split(" ")
        if (parts.size != 2) return 0
        
        val timePart = parts[0] // "09:00"
        val amPm = parts[1].uppercase() // "AM" 또는 "PM"
        
        val timeParts = timePart.split(":")
        if (timeParts.size != 2) return 0
        
        val hour = timeParts[0].toIntOrNull() ?: return 0
        val minute = timeParts[1].toIntOrNull() ?: return 0
        
        // 24시간 형식으로 변환
        val hour24 = when {
            amPm == "AM" && hour == 12 -> 0 // 12:00 AM = 00:00
            amPm == "AM" -> hour // 1:00 AM ~ 11:59 AM
            amPm == "PM" && hour == 12 -> 12 // 12:00 PM = 12:00
            amPm == "PM" -> hour + 12 // 1:00 PM ~ 11:59 PM = 13:00 ~ 23:59
            else -> hour
        }
        
        // 분 단위로 변환 (00:00 = 0분, 23:59 = 1439분)
        return hour24 * 60 + minute
    } catch (e: Exception) {
        return 0
    }
}

@Composable
fun TodoListComponent(
    todoItems: List<TodoItem>,
    onStartClick: (TodoItem) -> Unit,
    onCallClick: (TodoItem) -> Unit, // 전화 예약 todo 클릭 시 호출
    modifier: Modifier = Modifier
) {
    // 전화 예약 todo와 일반 todo 분리
    val callTodos = todoItems.filter { it.hasCall && it.reservedTime != null }
        .sortedBy { parseTimeToMinutes(it.reservedTime ?: "") } // 시간을 분 단위로 변환해서 정렬
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

                    items(callTodos.size) { index ->
                        TimelineTodoItemRow(
                            todoItem = callTodos[index],
                            isFirst = index == 0,
                            isLast = index == callTodos.size - 1,
                            onStartClick = { onCallClick(callTodos[index]) }, // 전화 예약은 onCallClick 사용
                            onDoneClick = { onStartClick(callTodos[index]) } // Done 상태는 기존 로직 사용
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
                        RegularTodoItemRow(
                            todoItem = todoItem,
                            onStartClick = { onStartClick(todoItem) }
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(96.dp))
                }
            }
        }
    }
}

// 타임라인 형식의 전화 예약 todo 아이템
@Composable
private fun TimelineTodoItemRow(
    todoItem: TodoItem,
    isFirst: Boolean,
    isLast: Boolean,
    onStartClick: () -> Unit, // Start 버튼 (전화 실행)
    onDoneClick: () -> Unit   // Done 버튼 (완료 처리)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 시간 표시 (왼쪽)
        Text(
            text = todoItem.reservedTime ?: "",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = EarlyBirdTheme.colors.fontBlack
        )

        // 타임라인 선과 점
        Box(
            modifier = Modifier
                .width(24.dp)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            // 세로 연결선
            if (!isFirst || !isLast) {
                Canvas(
                    modifier = Modifier
                        .width(2.dp)
                        .fillMaxHeight()
                ) {
                    val strokeWidth = 4f
                    val color = Color(0xFFE0E0E0)

                    if (!isFirst) {
                        // 위쪽 선
                        drawLine(
                            color = color,
                            start = androidx.compose.ui.geometry.Offset(size.width / 2, 0f),
                            end = androidx.compose.ui.geometry.Offset(size.width / 2, size.height / 2),
                            strokeWidth = strokeWidth
                        )
                    }

                    if (!isLast) {
                        // 아래쪽 선
                        drawLine(
                            color = color,
                            start = androidx.compose.ui.geometry.Offset(size.width / 2, size.height / 2),
                            end = androidx.compose.ui.geometry.Offset(size.width / 2, size.height),
                            strokeWidth = strokeWidth
                        )
                    }
                }
            }

            // 중앙 점
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(
                        color = EarlyBirdTheme.colors.mainBlue,
                        shape = CircleShape
                    )
            )
        }
        Text(
            modifier = Modifier
                .weight(1f),
            text = todoItem.text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0E0E0E)
        )
        // 상태에 따른 버튼/아이콘 표시
        when (todoItem.status) {
            TodoStatus.NOT_STARTED -> {
                Button(
                    onClick = onStartClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1799BE),
                        contentColor = EarlyBirdTheme.colors.white,
                    ),
                    shape = RoundedCornerShape(28.dp),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .width(57.dp)
                        .height(48.dp)
                ) {
                    Text(
                        text = "Start",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            TodoStatus.IN_PROGRESS -> {
                Button(
                    onClick = onStartClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50),
                        contentColor = EarlyBirdTheme.colors.white,
                    ),
                    shape = RoundedCornerShape(28.dp),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .width(57.dp)
                        .height(48.dp)
                ) {
                    Text(
                        text = "Done",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            TodoStatus.COMPLETED -> {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = Color(0xFF4CAF50),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✓",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

// 일반 todo 아이템 (기존 형태 유지)
@Composable
private fun RegularTodoItemRow(
    todoItem: TodoItem,
    onStartClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 할일 내용 (왼쪽에서 시작)
        Text(
            modifier = Modifier.weight(1f),
            text = todoItem.text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0E0E0E)
        )
//        Column(
//            modifier = Modifier.weight(1f)
//        ) {
//            Text(
//                text = todoItem.text,
//                fontSize = 16.sp,
//                fontWeight = FontWeight.Bold,
//                color = Color(0xFF0E0E0E)
//            )
//
//            // 타이머나 기타 정보 표시
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                todoItem.timerDuration?.let { duration ->
//                    Text(
//                        text = duration,
//                        fontSize = 12.sp,
//                        fontWeight = FontWeight.Medium,
//                        color = Color(0xFF666666)
//                    )
//                }
//
//                if (todoItem.hasTimer) {
//                    Image(
//                        painter = painterResource(id = R.drawable.main_timer_icon),
//                        contentDescription = "Timer",
//                        modifier = Modifier.size(14.dp)
//                    )
//                }
//            }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        // 상태에 따른 버튼/아이콘 표시
        when (todoItem.status) {
            TodoStatus.NOT_STARTED -> {
                Button(
                    onClick = onStartClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1799BE),
                        contentColor = EarlyBirdTheme.colors.white,
                    ),
                    shape = RoundedCornerShape(28.dp),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .width(57.dp)
                        .height(48.dp)
                ) {
                    Text(
                        text = "Start",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            TodoStatus.IN_PROGRESS -> {
                Button(
                    onClick = onStartClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50),
                        contentColor = EarlyBirdTheme.colors.white,
                    ),
                    shape = RoundedCornerShape(28.dp),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .width(57.dp)
                        .height(48.dp)
                ) {
                    Text(
                        text = "Done",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            TodoStatus.COMPLETED -> {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = Color(0xFF4CAF50),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✓",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
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
                // 전화 예약 todo (시간 순서대로 정렬되어 표시됨)
                TodoItem(1, "Call doctor", reservedTime = "09:00 AM", hasCall = true),
                TodoItem(2, "Meeting with client", reservedTime = "04:30 PM", hasCall = true),
                TodoItem(3, "Call mom", reservedTime = "02:00 PM", hasCall = true),
                TodoItem(4, "Early morning call", reservedTime = "07:30 AM", hasCall = true),
                
                // 일반 todo
                TodoItem(5, "Read for 30 minutes", timerDuration = "30 min", hasTimer = true),
                TodoItem(6, "Exercise for 20 minutes", timerDuration = "20 min", hasTimer = true),
                TodoItem(7, "Write journal entry"),
                TodoItem(8, "Grocery shopping")
            ),
            onStartClick = { /* Handle start click */ },
            onCallClick = { /* Handle call click */ }
        )
    }
}