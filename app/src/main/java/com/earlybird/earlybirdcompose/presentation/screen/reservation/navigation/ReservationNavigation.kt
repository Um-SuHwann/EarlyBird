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
        ReservationScreen()
    }
}
