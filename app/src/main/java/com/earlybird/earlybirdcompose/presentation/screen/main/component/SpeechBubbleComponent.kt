package com.earlybird.earlybirdcompose.presentation.screen.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdComposeTheme
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdTheme

@Composable
fun SpeechBubbleComponent(
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = EarlyBirdTheme.colors.white,
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 2.dp
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 6.dp),
            contentAlignment = Alignment.Center
        ) {
         Text(
              text = text,
              fontSize = 14.sp,
              fontWeight = FontWeight.Medium,
              color = EarlyBirdTheme.colors.fontBlack,
              textAlign = TextAlign.Center
          )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SpeechBubbleComponentPreview() {
    EarlyBirdComposeTheme {
        SpeechBubbleComponent(
            text = "Overthinking? Try 2 min. Let’s go\uD83C\uDFB6"
        )
    }
}