package com.earlybird.earlybirdcompose.presentation.screen.timer.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

//타이머 또는 종료 되었을 때 종료 버튼을 감싸는 컨테이너
@Composable
fun TimerContainer(
    content: @Composable BoxScope.() -> Unit
) {
    Surface(
        shape = RoundedCornerShape(100.dp),
        shadowElevation = 2.dp
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth()
                .background(Color.White),
            contentAlignment = Alignment.Center,
            content = content
        )
    }
}