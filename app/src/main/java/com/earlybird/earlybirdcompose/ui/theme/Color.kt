package com.earlybird.earlybirdcompose.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

//----------------------------------------------

// Blue Scale
val MainBlue = Color(0xFF00A2CB)

val WHITE = Color(0xFFFFFFFF)
val BLACK = Color(0xFF000000)
val FontBlack = Color(0xFF4C4C4C)


val defaultEarlyBirdColors = EarlyBirdColors(
    mainBlue = MainBlue,
    white = WHITE,
    black = BLACK,
    fontBlack = FontBlack,
)

@Immutable
data class EarlyBirdColors(
    val mainBlue: Color,
    val white: Color,
    val black: Color,
    val fontBlack: Color,
)

val LocalEarlyBirdColors = staticCompositionLocalOf { defaultEarlyBirdColors }