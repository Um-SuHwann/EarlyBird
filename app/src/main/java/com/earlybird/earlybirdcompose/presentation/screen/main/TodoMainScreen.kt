package com.earlybird.earlybirdcompose.presentation.screen.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.earlybird.earlybirdcompose.R
import com.earlybird.earlybirdcompose.presentation.screen.main.component.BirdImageComponent
import com.earlybird.earlybirdcompose.presentation.screen.main.component.DateComponent
import com.earlybird.earlybirdcompose.presentation.screen.main.component.ModeToggleComponent
import com.earlybird.earlybirdcompose.presentation.screen.main.component.SpeechBubbleComponent
import com.earlybird.earlybirdcompose.presentation.screen.main.component.TodoListComponent
import com.earlybird.earlybirdcompose.presentation.screen.main.component.TodoStatus
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdComposeTheme
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdTheme

@Composable
fun MainScreen(
    onModeToggle: () -> Unit = {},
    onAddTodoClick: () -> Unit = {},
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val backgroundColor = EarlyBirdTheme.colors.white
    val uiState by viewModel.uiState.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(WindowInsets.systemBars.asPaddingValues())
    ) {
        // 왼쪽 상단 - DateComponent
        DateComponent(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 20.dp, top = 20.dp),
            dayStreak = uiState.dayStreak
        )

        // 오른쪽 상단 - Mode Toggle
        ModeToggleComponent(
            isSimpleMode = false,
            onToggle = onModeToggle,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 20.dp, top = 20.dp)
        )
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 96.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BirdImageComponent(
                dayStreak = uiState.dayStreak,
                modifier = Modifier
            )
            SpeechBubbleComponent(
                text = "Overthinking? Try 2 min. Let's go\uD83C\uDFB6",
                modifier = Modifier.padding(top = 16.dp)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 20.dp)
                    .padding(top = 40.dp)
            ){
                TodoListComponent(
                    todoItems = uiState.todoItems,
                    //start 버튼 눌렀을 때
                    onStartClick = { todoItem ->
                        when (todoItem.status) {
                            TodoStatus.NOT_STARTED -> {
                                viewModel.updateTodoStatus(todoItem.id, TodoStatus.IN_PROGRESS)
                                // 여기서 타이머나 집중 세션을 시작할 수도 있음
                            }
                            TodoStatus.IN_PROGRESS -> {
                                // Done 버튼 클릭 → 완료 처리
                                viewModel.updateTodoStatus(todoItem.id, TodoStatus.COMPLETED)
//                                viewModel.markTodoAsCompleted(todoItem.id)
                            }
                            TodoStatus.COMPLETED -> {
                                // 완료 상태에서는 클릭 무시
                            }
                        }
                    },
                    modifier = Modifier.padding(top = 24.dp)
                )
            }
        }
        
//        // Clear All Button (개발/테스트 용도)
//        FloatingActionButton(
//            onClick = { viewModel.clearAllTodos() },
//            modifier = Modifier
//                .align(Alignment.BottomStart)
//                .padding(30.dp)
//                .size(48.dp),
//            containerColor = Color(0xFFE53E3E),
//            shape = CircleShape
//        ) {
//            androidx.compose.material3.Text(
//                text = "✕",
//                color = Color.White,
//                fontSize = 20.sp
//            )
//        }
        
        // FloatingActionButton for adding todo
        FloatingActionButton(
            onClick = onAddTodoClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(30.dp)
                .size(64.dp),
            containerColor = Color(0xFF06518A),
            shape = CircleShape
        ) {
            Image(
                painter = painterResource(id = R.drawable.main_plus_icon),
                contentDescription = "Add Todo",
                modifier = Modifier.size(32.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview(){
    EarlyBirdComposeTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MainScreen()
        }
    }
}