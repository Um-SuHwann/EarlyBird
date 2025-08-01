package com.earlybird.earlybirdcompose.presentation.screen.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdComposeTheme
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdTheme

@Composable
fun ModeToggleComponent(
    isSimpleMode: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(EarlyBirdTheme.colors.mainBlue, Color(0xFF37C3EA))
                ),
                shape = RoundedCornerShape(30.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        // Todo 부분 (왼쪽)
        Box(
            modifier = Modifier
                .background(
                    color = if (!isSimpleMode) EarlyBirdTheme.colors.white else Color.Transparent,
                    shape = RoundedCornerShape(30.dp)
                )
                .padding(horizontal = 12.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "to-do",
                color = if (!isSimpleMode) EarlyBirdTheme.colors.black else EarlyBirdTheme.colors.white,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        // Simple 부분 (오른쪽)
        Box(
            modifier = Modifier
                .background(
                    color = if (isSimpleMode) EarlyBirdTheme.colors.white else Color.Transparent,
                    shape = RoundedCornerShape(30.dp)
                )
                .padding(horizontal = 12.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "simple",
                color = if (isSimpleMode) EarlyBirdTheme.colors.black else EarlyBirdTheme.colors.white,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ModeToggleComponentPreview() {
    EarlyBirdComposeTheme {
        ModeToggleComponent(
            isSimpleMode = true,
            onToggle = {}
        )
    }
}