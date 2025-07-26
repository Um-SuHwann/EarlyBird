package com.earlybird.earlybirdcompose.presentation.screen.reservation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.earlybird.earlybirdcompose.R
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdTheme

@Composable
fun TopSpeechBubble(
    leftImageRes: Int,
    speechText: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // 왼쪽 이미지
        Image(
            painter = painterResource(id = leftImageRes),
            contentDescription = null,
            modifier = Modifier.width(68.dp).height(87.dp)
        )
        
        // 오른쪽 말풍선
        Box(
            modifier = Modifier.weight(1f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.reservation_bubble),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 12.dp),
                contentScale = ContentScale.FillWidth
            )
            Text(
                text = speechText,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = 12.dp)
                    .padding(start = 26.dp, end = 16.dp),
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = EarlyBirdTheme.colors.fontBlack,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopSpeechBubblePreview() {
    TopSpeechBubble(
        leftImageRes = R.drawable.reservation_bird_icon,
        speechText = "안녕하세요! 이것은 말풍선입니다."
    )
}