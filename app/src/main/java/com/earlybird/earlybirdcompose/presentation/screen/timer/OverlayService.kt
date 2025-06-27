package com.earlybird.earlybirdcompose.presentation.screen.timer

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.earlybird.earlybirdcompose.MainActivity
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdComposeTheme

class OverlayService : Service() {
    private lateinit var windowManager: WindowManager
    private var composeView: ComposeView? = null
    private var overlayLifecycle: OverlayLifecycle? = null

    override fun onCreate() {
        super.onCreate()
        Log.d("overlayService", "onCreate() 호출됨")
        try {
            overlayLifecycle = OverlayLifecycle()
            Log.d("overlayService", "OverlayLifecycle 생성 완료")
            
            windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
            Log.d("overlayService", "WindowManager 초기화 성공")
            
            showOverlay()
        } catch (e: ClassCastException) {
            Log.e("overlayService", "WindowManager 캐스팅 오류: ${e.message}")
            e.printStackTrace()
            stopSelf()
        } catch (e: Exception) {
            Log.e("overlayService", "onCreate() 오류: ${e.message}")
            e.printStackTrace()
            stopSelf()
        }
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("overlayService", "onStartCommand() 호출됨")
        return super.onStartCommand(intent, flags, startId)
    }
    
    override fun onDestroy() {
        super.onDestroy()
        Log.d("overlayService", "onDestroy() 호출됨")
        overlayLifecycle?.destroy()
        removeOverlay()
    }
    
    override fun onBind(intent: Intent): IBinder? = null

    private fun showOverlay() {
        Log.d("overlayService", "showOverlay() 시작")
        if(composeView != null) {
            Log.d("overlayService", "composeView가 이미 존재함")
            return
        }
        
        // 권한 체크
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val hasPermission = android.provider.Settings.canDrawOverlays(this)
            Log.d("overlayService", "오버레이 권한 상태: $hasPermission")
            if (!hasPermission) {
                Log.e("overlayService", "오버레이 권한이 없습니다!")
                stopSelf()
                return
            }
        }
        
        Log.d("overlayService", "ComposeView 생성 시작")
        try {
            // WindowManager.LayoutParams 설정
            val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                else
                    WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                PixelFormat.TRANSLUCENT
            )
            params.gravity = Gravity.CENTER
            Log.d("overlayService", "WindowManager.LayoutParams 생성 완료")
            
            // ComposeView를 생성하고 설정
            composeView = ComposeView(this).apply {
                Log.d("overlayService", "ComposeView 생성됨")
                // ViewCompositionStrategy 설정 - 다른 전략 사용
                setViewCompositionStrategy(
                    ViewCompositionStrategy.DisposeOnDetachedFromWindow
                )
            }
            
            // ComposeView에 LifecycleOwner와 SavedStateRegistryOwner 설정
            composeView?.let { view ->
                view.setViewTreeLifecycleOwner(overlayLifecycle)
                
                // SavedStateRegistry가 초기화된 경우에만 설정
                try {
                    view.setViewTreeSavedStateRegistryOwner(overlayLifecycle)
                    Log.d("overlayService", "LifecycleOwner 및 SavedStateRegistryOwner 설정 완료")
                } catch (e: Exception) {
                    Log.w("overlayService", "SavedStateRegistry 설정 실패, LifecycleOwner만 사용: ${e.message}")
                    Log.d("overlayService", "LifecycleOwner 설정 완료")
                }
            }
            
            try {
                Log.d("overlayService", "windowManager.addView() 호출 시작")
                windowManager.addView(composeView, params)
                Log.d("overlayService", "windowManager.addView() 성공!")
                if(composeView == null)
                    Log.e("overlayService", "❌ ComposeView is NULL before post")
                // WindowManager에 추가된 후 setContent 호출
                Log.d("overlayService", "ComposeView setContent 호출 시작")
                composeView?.viewTreeObserver?.addOnGlobalLayoutListener {
                    Log.d("overlayService", "onGlobalLayout → ComposeView가 attach됨")

                    composeView?.setContent {
                        Log.d("overlayService", "ComposeView setContent 내부 실행")
                        EarlyBirdComposeTheme {
                            TimerOverlayContent(
                                onTimerDoneClick = {
                                    val intent = Intent(this@OverlayService, MainActivity::class.java).apply {
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    }
                                    startActivity(intent)
                                    stopSelf()
                                }
                            )
                        }
                    }
                }
                Log.d("overlayService", "ComposeView setContent 완료")
                
            } catch (e: SecurityException) {
                Log.e("overlayService", "권한 오류: ${e.message}")
                e.printStackTrace()
                stopSelf()
            } catch (e: Exception) {
                Log.e("overlayService", "오버레이 생성 오류: ${e.message}")
                e.printStackTrace()
                stopSelf()
            }
            
        } catch (e: Exception) {
            Log.e("overlayService", "ComposeView 생성 오류: ${e.message}")
            e.printStackTrace()
            stopSelf()
            return
        }
    }
    
    private fun removeOverlay() {
        Log.d("overlayService", "removeOverlay() 호출됨")

        composeView?.let {
            try {
                windowManager.removeView(it)
                Log.d("overlayService", "오버레이 제거 성공")
            } catch (e: Exception) {
                Log.e("overlayService", "오버레이 제거 오류: ${e.message}")
            }
            composeView = null
        }
    }
}

@Composable
fun TimerOverlayContent(onTimerDoneClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // TimerScreen을 배경으로 표시
        TimerScreen(
            onTimerDoneClick = onTimerDoneClick
        )
    }
}