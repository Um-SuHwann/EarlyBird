package com.earlybird.earlybirdcompose.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.earlybird.earlybirdcompose.R
import android.Manifest
import android.graphics.BitmapFactory
import android.util.Log

object NotificationHelper {

    private const val DEFAULT_CHANNEL_ID = "alarm_channel"
    private const val DEFAULT_CHANNEL_NAME = "일반 알림"
    private const val DEFAULT_CHANNEL_DESC = "기본 푸시 알림 채널"

    fun showNotification(
        context: Context,
        title: String,
        message: String,
        notificationId: Int = 100,
        vibration: Boolean = false,
        channelId: String = DEFAULT_CHANNEL_ID,
        channelName: String = DEFAULT_CHANNEL_NAME,
        channelDesc: String = DEFAULT_CHANNEL_DESC,
        smallIconRes: Int// 아이콘 변경 가능
    ) {
        createNotificationChannel(context, channelId, channelName, channelDesc, vibration)

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(smallIconRes)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, smallIconRes))
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true) //사용자가 탭하면 자동으로 알림 제거
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        Log.d("AlarmScheduler","알람 만들기")
        // 진동 옵션
        if (vibration) {
            builder.setVibrate(longArrayOf(0, 500, 500, 500))
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(context).notify(notificationId, builder.build())
        } else {
            Log.d("AlarmScheduler","실패함")
        }
    }

    private fun createNotificationChannel(
        context: Context,
        channelId: String,
        channelName: String,
        channelDesc: String,
        vibration: Boolean
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDesc
                enableVibration(vibration)
                if (vibration) {
                    vibrationPattern = longArrayOf(0, 500, 500, 500)
                }
            }
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}