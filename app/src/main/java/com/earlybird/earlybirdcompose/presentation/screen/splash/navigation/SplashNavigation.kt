package com.earlybird.earlybirdcompose.presentation.screen.splash.navigation

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.earlybird.earlybirdcompose.presentation.screen.splash.SplashRoute

fun NavGraphBuilder.splashScreen(
    navController: NavController,
    startIntent: Intent,
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit,
) {
    composable("splash") {
        SplashRoute(
            navController = navController,
            startIntent = startIntent,
            onLoginRequired = navigateToLogin,
            onAlreadyLoggedIn = navigateToHome,
        )
    }
}