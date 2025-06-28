package com.earlybird.earlybirdcompose.presentation.screen.reservation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.earlybird.earlybirdcompose.R
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdTheme

@Composable
fun TodoSpeechBubble(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.then(
            Modifier.padding(top = 10.dp)
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
                .width(282.dp)
                .height(40.dp)
        ) {
            Text(text = text)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoSpeechBubblePreview(){
    TodoSpeechBubble("")
}