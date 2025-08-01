package com.earlybird.earlybirdcompose.presentation.screen.main.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.earlybird.earlybirdcompose.R
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdComposeTheme

@Composable
fun BirdImageComponent(
    dayStreak: Int,
    modifier: Modifier = Modifier
) {
    val birdImageRes = getBirdImageByStreak(dayStreak)
    
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // 그림자 이미지 (아래쪽에 약간 오프셋)
        Image(
            painter = painterResource(id = R.drawable.main_bird_shadow),
            contentDescription = "Bird shadow",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .width(150.dp)
                .offset(y = 20.dp)
        )
        
        // 새 이미지 (위쪽)
        Image(
            painter = painterResource(id = birdImageRes),
            contentDescription = "Bird image for $dayStreak day streak",
            modifier = Modifier
                .width(136.dp)
                .height(170.dp)
        )
    }
}

private fun getBirdImageByStreak(dayStreak: Int): Int {
    return when {
        dayStreak <= 3 -> R.drawable.main_bird_icon // 기본 새 이미지 (1-3일)
        dayStreak <= 7 -> R.drawable.main_bird_icon // 향후 다른 새 이미지로 변경 예정 (4-7일)
        dayStreak <= 14 -> R.drawable.main_bird_icon // 향후 다른 새 이미지로 변경 예정 (8-14일)
        dayStreak <= 30 -> R.drawable.main_bird_icon // 향후 다른 새 이미지로 변경 예정 (15-30일)
        else -> R.drawable.main_bird_icon // 향후 다른 새 이미지로 변경 예정 (30일 이상)
    }
}

@Preview(showBackground = true)
@Composable
fun BirdImageComponentPreview() {
    EarlyBirdComposeTheme {
        BirdImageComponent(dayStreak = 7)
    }
}