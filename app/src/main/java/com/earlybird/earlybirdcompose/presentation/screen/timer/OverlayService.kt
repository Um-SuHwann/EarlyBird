package com.earlybird.earlybirdcompose.presentation.screen.timer

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.activity.setViewTreeOnBackPressedDispatcherOwner
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner

class OverlayService : Service() {
    private lateinit var windowManager: WindowManager
    private lateinit var composeView: ComposeView
    private lateinit var overlayView: ComposeWrapperView

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        overlayView = ComposeWrapperView(this)

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else
                WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.CENTER

//        val lifecycleOwner = OverlayLifecycle()
//        composeView = ComposeView(this).apply {
//            // 커스텀 라이프사이클 소유자 설정
//            setViewTreeLifecycleOwner(lifecycleOwner)
//            setViewTreeSavedStateRegistryOwner(lifecycleOwner)
////            setViewTreeOnBackPressedDispatcherOwner(lifecycleOwner.onBackPressedDispatcher)
//            setContent {
//                // 컴포즈 UI 구성
//                MaterialTheme {
//                    Text("오버레이 타이머")
//                }
//            }
//        }
        windowManager.addView(overlayView, params)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeOverlay()
    }
    override fun onBind(intent: Intent): IBinder? = null

    private fun removeOverlay() {
        (composeView.findViewTreeLifecycleOwner() as? OverlayLifecycle)?.destroy()
        if (::windowManager.isInitialized && ::overlayView.isInitialized) {
            windowManager.removeView(overlayView)
        }
    }
}