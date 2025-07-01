package com.earlybird.earlybirdcompose.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log

fun checkPermission(
    context: Context,
    content: String,
    buttonContent: String,
    durationMillis: Int,
    isFinished: Boolean
) {
    Log.d("MainScreen", "checkPermission() 호출됨")

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {  // 마시멜로우 이상일 경우
        Log.d("MainScreen", "Android M 이상 버전")
        if (!Settings.canDrawOverlays(context)) {  // 오버레이 권한 체크
            Log.d("MainScreen", "오버레이 권한이 없음 - 권한 요청 화면으로 이동")
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:${context.packageName}")
            )
            (context as Activity).startActivityForResult(intent, 0)
        } else {
            Log.d("MainScreen", "오버레이 권한 있음 - OverlayService 시작")
            startOverlayService(
                context = context,
                content = content,
                buttonContent = buttonContent,
                durationMillis = durationMillis,
                isFinished = isFinished
            )
        }
    } else {
        Log.d("MainScreen", "Android M 미만 버전 - OverlayService 시작")
        startOverlayService(
            context = context,
            content = content,
            buttonContent = buttonContent,
            durationMillis = durationMillis,
            isFinished = isFinished
        )
    }
}