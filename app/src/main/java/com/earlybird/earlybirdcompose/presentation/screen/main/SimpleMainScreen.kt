package com.earlybird.earlybirdcompose.presentation.screen.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.foundation.border
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.earlybird.earlybirdcompose.R
import com.earlybird.earlybirdcompose.presentation.screen.main.component.DateComponent
import com.earlybird.earlybirdcompose.presentation.screen.main.component.ModeToggleComponent
import com.earlybird.earlybirdcompose.presentation.screen.main.component.NextCallReminderComponent
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdComposeTheme
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdTheme

@Composable
fun SimpleMainScreen(
    onModeToggle: () -> Unit = {},
    onStartTimer: () -> Unit = {},
    onGoToTodoMain: () -> Unit = {},
    nextCallTime: Long? = null,
    nextCallTodoTitle: String? = null,
    viewModel: MainViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val backgroundColor = EarlyBirdTheme.colors.white
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
            isSimpleMode = true,
            onToggle = onModeToggle,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 20.dp, top = 20.dp)
        )
        
        //중간 이미지
        Image(
            painter = painterResource(id = R.drawable.origin_main_bird_icon),
            contentDescription = "Bird shadow",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 150.dp)
                .width(350.dp)
        )
        
        // 하단 영역 (다음 통화 알림 + 버튼들)
        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = EarlyBirdTheme.colors.white)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp).padding(top = 20.dp, bottom = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 다음 통화 알림 (알람 설정된 할일이 있을 때만 표시)
                NextCallReminderComponent(
                    nextCallTime = nextCallTime,
                    todoTitle = nextCallTodoTitle,
                    modifier = Modifier.fillMaxWidth()
                )

                // 액션 버튼들
                ActionButton(
                    text = "Start now",
                    onClick = onStartTimer,
                    isPrimary = true,
                    modifier = Modifier
                )

                ActionButton(
                    text = "Edit",
                    onClick = onGoToTodoMain,
                    isPrimary = false,
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun ActionButton(
    text: String,
    onClick: () -> Unit,
    isPrimary: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(66.dp)
            .background(
                brush = if (isPrimary) {
                    Brush.linearGradient(
                        colors = listOf(EarlyBirdTheme.colors.mainBlue, Color(0xFF0076C6))
                    )
                } else {
                    Brush.linearGradient(
                        colors = listOf(EarlyBirdTheme.colors.white, Color(0xFFF5F5F5))
                    )
                },
                shape = RoundedCornerShape(8.dp)
            )
            .then(
                if (!isPrimary) {
                    Modifier.border(
                        width = 1.dp,
                        color = EarlyBirdTheme.colors.black.copy(alpha = 0.3F),
                        shape = RoundedCornerShape(8.dp)
                    )
                } else Modifier
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = if (isPrimary) EarlyBirdTheme.colors.white else EarlyBirdTheme.colors.mainBlue
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SimpleMainScreenPreview() {
    EarlyBirdComposeTheme {
        SimpleMainScreen()
    }
}