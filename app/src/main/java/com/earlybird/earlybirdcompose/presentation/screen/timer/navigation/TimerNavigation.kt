package com.earlybird.earlybirdcompose.presentation.screen.timer.navigation

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.earlybird.earlybirdcompose.presentation.screen.timer.TimerScreen

fun NavGraphBuilder.timerGraph(
    navController: NavController,
    startIntent: Intent,
) {
    composable("timer_overlay") {
//        TimerScreen()
    }
}