package com.earlybird.earlybirdcompose.presentation.screen.splash

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdComposeTheme
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdTheme

@Composable
fun SplashScreen(){
    val backgroundColor = EarlyBirdTheme.colors.white
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color(0xFF0FA7CE))){
                    append("얼리버드")
                }
                append("와 함께라면\n")
                withStyle(style = SpanStyle(color = Color(0xFF0FA7CE))){
                    append("시작이 어렵지 않아요")
                }
            },
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold,
            color = EarlyBirdTheme.colors.black,
            lineHeight = 42.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenPreview(){
    EarlyBirdComposeTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            SplashScreen()
        }
    }
}