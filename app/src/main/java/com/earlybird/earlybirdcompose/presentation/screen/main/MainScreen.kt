package com.earlybird.earlybirdcompose.presentation.screen.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.earlybird.earlybirdcompose.R
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdComposeTheme
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun MainScreen(
    onSelectTimeClick: () -> Unit = {},
    onStartNowClick: () -> Unit = {},
    onSettingClick: () -> Unit = {}
) {
    val backgroundColor = Brush.verticalGradient(
        colors = listOf(Color(0xFFEAF7FA), Color(0xFF8CE6FF))
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundColor)
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
        // 현재 날짜
        Text(
            text = getTodayDateFormatted(),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF5A5A5A),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 109.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(17.dp)
        ){
            Surface(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 179.dp),
                color = EarlyBirdTheme.colors.white,
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 2.dp
            ) {
                EditableMotivationalBox()
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 270.dp, start = 10.dp)
        ){
            Image(
                painter = painterResource(R.drawable.main_bird_icon),
                contentDescription = "main 캐릭터",
                modifier = Modifier
                    .width(356.dp)
                    .height(220.dp),
                contentScale = ContentScale.Fit
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            Surface(
                color = EarlyBirdTheme.colors.white,
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp, bottomStart = 0.dp, bottomEnd = 0.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                ) {
                    MainActionButton(
                        text = "원하는 시간에 시작하기 >",
                        backgroundColor = Color(0xFF0FA7CE),
                        textColor = EarlyBirdTheme.colors.white,
                        iconColor = EarlyBirdTheme.colors.mainBlue,
                        onClick = { /* 원하는 시간에 시작하기 클릭 시 동작 */ }
                    )
                    MainActionButton(
                        text = "지금 당장 시작하기 >",
                        backgroundColor = Color.White,
                        textColor = EarlyBirdTheme.colors.mainBlue,
                        iconColor = EarlyBirdTheme.colors.white,
                        onClick = onStartNowClick
                    )
                }
            }
        }
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

@Composable
fun HeaderSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp) // 설정 버튼 아래
            .background(
                color = Color.White,
                shape = RoundedCornerShape(20.dp)
            ),
    ) {
        Text(
            text = "6월 18일 수요일",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
        ChatBubble(text = "나는 할 일을 미루지 않는 사람이 될거야!", isLeft = true, color = Color(0xFF0288D1), textColor = Color.White)
    }
}

fun getTodayDateFormatted(): String {
    val today = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("M월 d일 E요일", Locale.KOREAN)
    return today.format(formatter)
}


@Composable
fun ChatBubble(
    text: String,
    isLeft: Boolean = true,
    color: Color = if (isLeft) Color(0xFFE0F7FA) else Color(0xFFFFFFFF),
    textColor: Color = Color.Black
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (isLeft) Arrangement.Start else Arrangement.End
    ) {
        Surface(
            color = color,
            shape = RoundedCornerShape(20.dp),
            shadowElevation = 2.dp
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                fontSize = 14.sp,
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