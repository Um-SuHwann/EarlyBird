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
//                val content = "Woohoo! We made it\nCan't wait to try again \uD83D\uDC23"
//                val buttonContent = "Done"
//                val durationMillis = 2 * 1000 * 60
//                navController.navigate("timer_overlay/$content/$buttonContent/$durationMillis")
            },
            onGoToTodoMain = {
                navController.navigate("main")
            }
        )
    }
}
