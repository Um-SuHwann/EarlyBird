package com.earlybird.earlybirdcompose.presentation.screen.reservation.navigation

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.earlybird.earlybirdcompose.presentation.screen.reservation.ReservationScreen
import java.net.URLEncoder

fun NavGraphBuilder.reservationGraph(
    navController: NavController,
    startIntent: Intent,
) {
    composable("reservation") {
        ReservationScreen(
            onBackClick = {
                navController.navigate("main"){
                    popUpTo("reservation") { inclusive = true }
                    launchSingleTop = true //main 화면이 스택에 이미 있다면 새로 넣지 않음
                }
            },
            onNavigateToTimer = { content, buttonContent, durationMillis ->
                val encodedContent = URLEncoder.encode(content, "UTF-8").replace("+", "%20")
                val encodedButtonContent = URLEncoder.encode(buttonContent, "UTF-8")
                navController.navigate("timer_overlay/${encodedContent}/${encodedButtonContent}/${durationMillis}") {
                    popUpTo("reservation") { inclusive = true }
                }
            }
        )
    }
}
