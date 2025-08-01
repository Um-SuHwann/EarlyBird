package com.earlybird.earlybirdcompose.presentation.screen.main.navigation

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.earlybird.earlybirdcompose.presentation.screen.main.MainScreen
import com.earlybird.earlybirdcompose.presentation.screen.main.SimpleMainScreen

fun NavGraphBuilder.mainGraph(
    navController: NavController,
    startIntent: Intent,
) {
    composable("main") {
        MainScreen(
            onAddTodoClick = {
                navController.navigate("reservation")
            }
        )
    }
    composable("simple_main"){
        SimpleMainScreen(
            onStartTimer = {

            },
            onGoToTodoMain = {
                navController.navigate("main")
            }
        )
    }
}
