package com.earlybird.earlybirdcompose.presentation.screen.main.navigation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.earlybird.earlybirdcompose.presentation.screen.main.MainScreen
import com.earlybird.earlybirdcompose.presentation.screen.splash.SplashRoute
import com.earlybird.earlybirdcompose.presentation.screen.timer.TimerScreen

fun NavGraphBuilder.mainGraph(
    navController: NavController,
    startIntent: Intent,
) {
    composable("main") {
        MainScreen(
            onStartNowClick = {
                navController.navigate("timer_overlay")
            }
        )
    }
}
