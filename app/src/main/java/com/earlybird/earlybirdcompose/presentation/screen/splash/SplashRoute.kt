package com.earlybird.earlybirdcompose.presentation.screen.splash

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun SplashRoute(
    navController: NavController,
    startIntent: Intent,
    onLoginRequired: () -> Unit,
    onAlreadyLoggedIn: () -> Unit,
) {
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("main") {
            popUpTo("splash") { inclusive = true }
        }
    }
    SplashScreen()
}