package com.earlybird.earlybirdcompose.util

import android.content.Context
import android.content.Intent
import com.earlybird.earlybirdcompose.presentation.screen.timer.OverlayService

fun startOverlayService(
    context: Context,
    content: String,
    buttonContent: String,
    durationMillis: Int,
    isFinished: Boolean = false
) {
    val intent = Intent(context, OverlayService::class.java).apply {
        putExtra("CONTENT", content)
        putExtra("BUTTON_CONTENT", buttonContent)
        putExtra("TIMER_DURATION", durationMillis)
        putExtra("IS_FINISHED", isFinished)
    }
    context.startService(intent)
}