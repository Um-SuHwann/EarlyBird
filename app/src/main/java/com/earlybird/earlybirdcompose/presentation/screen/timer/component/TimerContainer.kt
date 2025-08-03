package com.earlybird.earlybirdcompose.presentation.screen.timer.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdComposeTheme
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdTheme

//타이머 또는 종료 되었을 때 종료 버튼을 감싸는 컨테이너
@Composable
fun TimerContainer(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Surface(
        shape = RoundedCornerShape(100.dp),
        color = Color.White.copy(alpha = 0.9f),
        shadowElevation = 2.dp,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
            content = content
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TimerContainerPreview() {
    EarlyBirdComposeTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            TimerContainer {
                Text(
                    text = "Preview Content",
                    color = Color.Black
                )
            }
        }
    }
}