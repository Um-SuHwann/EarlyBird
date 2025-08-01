package com.earlybird.earlybirdcompose.presentation.screen.reservation.component

import android.media.ImageReader
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
    selectedMood: Mood?,
    modifier: Modifier = Modifier
) {
    // 말풍선 텍스트 로직을 여기로 이동
    val speechText = if (selectedMood != null) {
        when (selectedMood) {
            Mood.BAD -> "Though day... Try just one small thing."
            Mood.NORMAL -> "Middle mood! still room to move."
            Mood.GOOD -> "Energy is here! Use it your way"
        }
    } else {
        "How are you feeling today?"
    }
    val ImageRes = if(selectedMood != null){
        when(selectedMood){
            Mood.BAD -> R.drawable.reservation_bird_bad
            Mood.NORMAL -> R.drawable.reservation_bird_normal
            Mood.GOOD -> R.drawable.reservation_bird_good
        }
    } else {
        R.drawable.reservation_bird_basic
    }
    Column (
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 왼쪽 이미지
        Image(
            painter = painterResource(id = ImageRes),
            contentDescription = null,
            modifier = Modifier
                .height(160.dp)
                .then(
                    if(selectedMood != null){
                        Modifier.offset(x = 56.dp)
                    } else {
                        Modifier
                    }
                )
        )
        Spacer(Modifier.height(14.dp))
        // 오른쪽 말풍선
        Box(
            modifier = Modifier
        ) {
            Image(
                painter = painterResource(id = R.drawable.reservation_bubble),
                contentDescription = null,
                modifier = Modifier
                    .width(335.dp),
                contentScale = ContentScale.FillWidth
            )
            Text(
                text = speechText,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = 8.dp)
                    .padding(horizontal = 20.dp),
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                color = EarlyBirdTheme.colors.fontBlack,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopSpeechBubblePreview() {
    TopSpeechBubble(
        selectedMood = Mood.GOOD
    )
}