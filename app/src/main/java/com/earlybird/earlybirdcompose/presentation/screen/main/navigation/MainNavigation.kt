package com.earlybird.earlybirdcompose.presentation.screen.main.navigation

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.earlybird.earlybirdcompose.presentation.screen.main.MainScreen

fun NavGraphBuilder.mainGraph(
    navController: NavController,
    startIntent: Intent,
) {
    composable("main") {
        MainScreen(
            onSelectTimeClick = {
                navController.navigate("reservation")
            },
            onStartNowClick = {
                navController.navigate("timer_overlay")
            }
        )
    }
}
