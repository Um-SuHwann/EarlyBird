package com.earlybird.earlybirdcompose.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.earlybird.earlybirdcompose.data.model.AlarmInfo
import java.util.Calendar

object AlarmScheduler {

    fun scheduleAlarm(
        context: Context,
        alarmType: AlarmType,
        alarmInfo: AlarmInfo? = null,
    ) {
        Log.d("Alarm","알람 예약")
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val hour = alarmInfo?.hour ?: alarmType.defaultHour
        val minute = alarmInfo?.minute ?: alarmType.defaultMinute
        val amPm = alarmInfo?.amPm ?: alarmType.defaultPa

        val hour24 = if (amPm == "PM" && hour != 12) hour + 12
                    else if (amPm == "AM" && hour == 12) 0
                    else hour

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("alarmInfo", alarmInfo)
            putExtra("requestCode", alarmType.requestCode)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarmType.requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // 알람 시간 설정
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour24)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            // 이미 지난 시간이면 내일로 설정
            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent //시간이 되었을 때 이게 실행되는거임
            )
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
    }

    fun cancelAlarm(
        context: Context,
        requestCode: Int = 0
    ) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }
}