package com.earlybird.earlybirdcompose.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.earlybird.earlybirdcompose.R

//예약 시간이 되면 호출되는 BroadcastReceiver

class  AlarmReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context, intent: Intent) {
        val hour : Int = intent.getIntExtra("hour", 9)
        val minute : Int = intent.getIntExtra("minute", 0)
        val pa : String  = intent.getStringExtra("pa") ?: "AM"
        val vibration : Boolean = intent.getBooleanExtra("vibration", false)
        val requestCode : Int = intent.getIntExtra("requestCode", -1)

        Log.d("AlarmScheduler","함수 진입")

        NotificationHelper.showNotification(
            context = context,
            title = "5분 남았어!",
            message = "5분 후에 같이 시작해보자구",
            vibration = vibration,
            notificationId = requestCode,
            smallIconRes = R.drawable.push_alarm_icon
        )
//        val alarmType = AlarmType.fromRequestCode(requestCode)
//        if (alarmType != null) {
//            AlarmScheduler.scheduleAlarm(
//                context, alarmType,  )
//        }
    }
}