package com.earlybird.earlybirdcompose.presentation.screen.call

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
            if (it.isNullOrBlank()) "할 일을" else it
        }
        setContent {
            EarlyBirdComposeTheme {
                CallScreen(
                    todoTask = todoTask,
                    onStartCall = {
                        com.earlybird.earlybirdcompose.util.checkPermission(
                            context = this,
                            content = "우와! 우리가 해냈다\n다음에도 같이 하자!",
                            buttonContent = "완료!",
                            durationMillis = 2 * 60 * 1000,
                            isFinished = false
                        )
                        finish()
                    }
                )
            }
        }
    }
}