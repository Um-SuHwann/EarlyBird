package com.earlybird.earlybirdcompose.presentation.screen.call

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdComposeTheme
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdTheme
import com.earlybird.earlybirdcompose.R

@Composable
fun CallScreen(
    callerName: String = "얼리버드",
    callState: String = "연결 중...",
    onStartCall: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010)),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.systemBars.asPaddingValues())
        ) {
            Spacer(modifier = Modifier.height(42.dp))
            Text(
                text = callerName,
                color = EarlyBirdTheme.colors.white,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(30.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = EarlyBirdTheme.colors.white.copy(alpha = 0.12f)
                    ),
                    border = BorderStroke(4.dp, EarlyBirdTheme.colors.white.copy(alpha = 0.2f))
                ) {
                    Column(
                        modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "외출준비 시작하기",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFF384)
                        )
                        Text(
                            text = "같이 해보자!",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = EarlyBirdTheme.colors.white
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(22.dp))
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(R.drawable.call_bird_calling_icon),
                contentScale = ContentScale.Fit,
                contentDescription = "새 이미지"
            )
        }
        // 하단 스와이프 버튼 될 예정
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp)
        ) {
            IconButton(
                onClick = onStartCall,
                modifier = Modifier
                    .size(72.dp)
                    .background(Color.Green, shape = CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_call),
                    contentDescription = "통화 시작",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CallScreenPreview() {
    EarlyBirdComposeTheme {
        CallScreen()
    }
}