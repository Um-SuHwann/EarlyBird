package com.earlybird.earlybirdcompose.presentation.screen.timer.navigation

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.earlybird.earlybirdcompose.presentation.screen.timer.TimerScreen

fun NavGraphBuilder.timerGraph(
    navController: NavController,
    startIntent: Intent,
) {
    composable(
        route = "timer_overlay/{content}/{buttonContent}/{durationMillis}",
        arguments = listOf(
            navArgument("content") { type = NavType.StringType },
            navArgument("buttonContent") { type = NavType.StringType },
            navArgument("durationMillis") { type = NavType.IntType }
        )
    ) { backStackEntry ->
        val content = backStackEntry.arguments?.getString("content") ?: ""
        val buttonContent = backStackEntry.arguments?.getString("buttonContent") ?: ""
        val durationMillis = backStackEntry.arguments?.getInt("durationMillis") ?: 0
        
        TimerScreen(
            content = content,
            buttonContent = buttonContent,
            durationMillis = durationMillis,
            isFinished = true,
            showMoodCheck = false,  // 알람 예약 시에는 기분 체크 안함
            onTimerDoneClick = {
                navController.navigate("main") {
                    popUpTo("timer_overlay") { inclusive = true }
                }
            }
        )
    }
}