/*
package com.earlybird.earlybirdcompose.presentation.screen.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun OnboardingScreen(navController: NavController) {
    // 3초 후 메인화면으로 이동
    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate("main") {
            popUpTo("onboarding") { inclusive = true }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "얼리버드와 함께라면\n시작이 어렵지 않아요",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    // NavController 미리보기용 더미
    // 실제 앱에서는 NavController를 전달해야 함
    // OnboardingScreen(navController)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "얼리버드와 함께라면\n시작이 어렵지 않아요",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )
    }
}

*/
