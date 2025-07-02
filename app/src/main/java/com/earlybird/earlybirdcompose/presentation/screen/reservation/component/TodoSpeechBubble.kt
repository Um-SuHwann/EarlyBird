package com.earlybird.earlybirdcompose.presentation.screen.reservation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.earlybird.earlybirdcompose.R
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoSpeechBubble(
    text: String,
    onTextChange: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 16.dp, end = 16.dp)
        ),
        contentAlignment = Alignment.TopCenter
    ) {
        // 꼬리 (삼각형)
        Image(
            painter = painterResource(R.drawable.reservation_bubble_tail_icon),
            contentDescription = "말풍선 꼬리",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(20.dp)
                .align(Alignment.TopCenter)
                .offset(y = -(10).dp)
        )
        // 말풍선 본체
        Box(
            modifier = Modifier
                .border(1.dp, EarlyBirdTheme.colors.fontBlack, RoundedCornerShape(100))
                .background(
                    color = EarlyBirdTheme.colors.white,
                    shape = RoundedCornerShape(100)
                )
                .fillMaxWidth()
                .height(56.dp),
        ) {
            androidx.compose.material3.TextField(
                value = text,
                onValueChange = {
                    if (it.length <= 25) onTextChange(it)
                },
                singleLine = true,
                placeholder = {
                    Text(
                        text = "여기를 눌러 같이 할 일을 입력해줘!",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 0.dp)
                    .align(Alignment.Center),
                colors = androidx.compose.material3.TextFieldDefaults.textFieldColors(
                    containerColor = EarlyBirdTheme.colors.white,
                    unfocusedIndicatorColor = EarlyBirdTheme.colors.white, // 밑줄 없애기
                    focusedIndicatorColor = EarlyBirdTheme.colors.white,   // 밑줄 없애기
                    disabledIndicatorColor = EarlyBirdTheme.colors.white
                ),
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontSize = 14.sp,  // 입력 글씨 크기 설정
                    fontWeight = FontWeight.Bold,
                    color = EarlyBirdTheme.colors.fontBlack,
                    textAlign = TextAlign.Center
                ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoSpeechBubblePreview(){
    TodoSpeechBubble("")
}