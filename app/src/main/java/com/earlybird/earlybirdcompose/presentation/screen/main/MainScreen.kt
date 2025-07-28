package com.earlybird.earlybirdcompose.presentation.screen.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.earlybird.earlybirdcompose.R
import com.earlybird.earlybirdcompose.presentation.screen.main.component.BirdImageComponent
import com.earlybird.earlybirdcompose.presentation.screen.main.component.DateComponent
import com.earlybird.earlybirdcompose.presentation.screen.main.component.SpeechBubbleComponent
import com.earlybird.earlybirdcompose.presentation.screen.main.component.TodoItem
import com.earlybird.earlybirdcompose.presentation.screen.main.component.TodoListComponent
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdComposeTheme
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdTheme
import com.earlybird.earlybirdcompose.util.checkPermission

@Composable
fun MainScreen(
    onSelectTimeClick: () -> Unit = {},
    onStartNowClick: () -> Unit = {},
    onSettingClick: () -> Unit = {},
    onAddTodoClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val backgroundColor = EarlyBirdTheme.colors.white
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(WindowInsets.systemBars.asPaddingValues())
    ) {
        // 설정 버튼 (오른쪽 상단)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, end = 20.dp),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = onSettingClick) {
                Icon(
                    painter = painterResource(id = R.drawable.setting_icon),
                    contentDescription = "설정",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified
                )
            }
        }
        // 현재 날짜 (왼쪽 위)
        DateComponent(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 20.dp, top = 20.dp),
            dayStreak = 5
        )
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 96.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BirdImageComponent(
                dayStreak = 5,
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
                    todoItems = listOf(
                        TodoItem(1, "Complete morning routine", reservedTime = "07:00 AM"),
                        TodoItem(2, "Read for 30 minutes", timerDuration = "30 min", hasTimer = true),
                        TodoItem(3, "Exercise for 20 minutes", reservedTime = "08:00 AM", timerDuration = "20 min", hasTimer = true),
                        TodoItem(4, "Call mom", hasCall = true),
                        TodoItem(5, "Plan tomorrow's schedule", timerDuration = "15 min", hasTimer = true, hasCall = true)
                    ),
                    onStartClick = { todoItem ->
                        // Handle todo item start click
                    },
                    modifier = Modifier.padding(top = 24.dp)
                )
            }
        }
        
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

//        Column(
//            verticalArrangement = Arrangement.spacedBy(20.dp),
//            modifier = Modifier
//                .align(Alignment.BottomCenter)
//        ) {
//            Surface(
//                color = EarlyBirdTheme.colors.white,
//                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp, bottomStart = 0.dp, bottomEnd = 0.dp),
//                modifier = Modifier
//                    .fillMaxWidth()
//            ) {
//                Column(
//                    verticalArrangement = Arrangement.spacedBy(16.dp),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 20.dp, vertical = 20.dp)
//                ) {
//                    MainActionButton(
//                        text = "원하는 시간에 시작하기 >",
//                        backgroundColor = Color(0xFF0FA7CE),
//                        textColor = EarlyBirdTheme.colors.white,
//                        iconColor = EarlyBirdTheme.colors.mainBlue,
//                        onClick = onSelectTimeClick
//                    )
//                    MainActionButton(
//                        text = "지금 당장 시작하기 >",
//                        backgroundColor = Color.White,
//                        textColor = EarlyBirdTheme.colors.mainBlue,
//                        iconColor = EarlyBirdTheme.colors.white,
//                        onClick = {
//                            //TimerScreen은 백그라운드 작업 또는 시스템 오버레이를 위해 사용되기 때문에 Navigation을 사용못함
//                            checkPermission(
//                                context = context,
//                                content = "우와! 우리가 해냈다\n다음에도 같이 하자!",
//                                buttonContent = "완료!",
//                                durationMillis = 2 * 60 * 1000,
//                                isFinished = false
//                            )
//                        }
//                    )
//                }
//            }
//        }
    }
}

@Composable
fun MainActionButton(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    iconColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
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