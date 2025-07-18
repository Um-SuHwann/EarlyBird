package com.earlybird.earlybirdcompose.presentation.screen.call.componenet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SwipeButton(
    onStartCall: () -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier
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
@Preview(showBackground = true)
@Composable
fun SwipeButtonPreview() {
    SwipeButton(
        onStartCall = {},
        modifier = Modifier
    )
}