package com.earlybird.earlybirdcompose

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.earlybird.earlybirdcompose.presentation.screen.main.MainScreen
import com.earlybird.earlybirdcompose.presentation.screen.main.navigation.mainGraph
import com.earlybird.earlybirdcompose.presentation.screen.splash.navigation.splashScreen

@Composable
fun AppNavigation(startIntent: Intent){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        splashScreen(
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
//        composable("login") { LoginScreen() }
        composable("main") {
            mainGraph(
                navController = navController,
                startIntent = startIntent,
            )
        }
    }
}