package com.earlybird.earlybirdcompose

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.earlybird.earlybirdcompose.presentation.screen.main.navigation.mainGraph
import com.earlybird.earlybirdcompose.presentation.screen.reservation.navigation.reservationGraph
import com.earlybird.earlybirdcompose.presentation.screen.splash.navigation.splashGraph
import com.earlybird.earlybirdcompose.presentation.screen.timer.navigation.timerGraph

@Composable
fun AppNavigation(startIntent: Intent){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        splashGraph(
            navController = navController,
            startIntent = startIntent,
            navigateToLogin = {
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            },
            navigateToHome = {
                navController.navigate("main") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        )
        mainGraph(
            navController = navController,
            startIntent = startIntent,
        )
        timerGraph(
            navController = navController,
            startIntent = startIntent,
        )
        reservationGraph(
            navController = navController,
            startIntent = startIntent,
        )
    }
}