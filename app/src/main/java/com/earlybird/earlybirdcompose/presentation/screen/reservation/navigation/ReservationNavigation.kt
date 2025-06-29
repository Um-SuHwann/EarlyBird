package com.earlybird.earlybirdcompose.presentation.screen.reservation.navigation

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.earlybird.earlybirdcompose.presentation.screen.main.MainScreen
import com.earlybird.earlybirdcompose.presentation.screen.reservation.ReservationScreen

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
            }
        )
    }
}
