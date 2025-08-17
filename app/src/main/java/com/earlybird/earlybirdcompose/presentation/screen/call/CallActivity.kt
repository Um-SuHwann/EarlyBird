package com.earlybird.earlybirdcompose.presentation.screen.call

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.earlybird.earlybirdcompose.MainActivity
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdComposeTheme

class CallActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            // 하위 버전용
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD)
        val todoTask = intent.getStringExtra("todoTask").let {
            if (it.isNullOrBlank()) "" else it
        }
        val durationMillis = intent.getIntExtra("durationMillis", 2).let { it * 60 * 1000 }
        Log.d("call", "$durationMillis")
        setContent {
            EarlyBirdComposeTheme {
                CallScreen(
                    todoTask = todoTask,
                    durationMillis = durationMillis,
                    onStartCall = {
                        com.earlybird.earlybirdcompose.util.checkPermission(
                            context = this,
                            content = "Woohoo! We made it\nCan't wait to try again \uD83D\uDC23",
                            buttonContent = "Done",
                            durationMillis = durationMillis,
                            isFinished = false
                        )
                        finish()
                    },
                    onNotNow = {
                        val intent = Intent(this, MainActivity::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        }
                        startActivity(intent)
                        finish()
                    }
                )
            }
        }
    }
}