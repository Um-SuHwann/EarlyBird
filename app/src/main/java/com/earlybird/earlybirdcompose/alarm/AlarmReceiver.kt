package com.earlybird.earlybirdcompose.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.util.Log
import com.earlybird.earlybirdcompose.R
import com.earlybird.earlybirdcompose.data.model.AlarmInfo
import com.earlybird.earlybirdcompose.presentation.screen.call.CallActivity

//예약 시간이 되면 호출되는 BroadcastReceiver

class  AlarmReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context, intent: Intent) {
        val alarmInfo = intent.getParcelableExtra<AlarmInfo>("alarmInfo")
        val requestCode : Int = intent.getIntExtra("requestCode", -1)

        if(requestCode == AlarmType.USER_REQUEST_CODE){
            NotificationHelper.showNotification(
                context = context,
                title = "5분 남았어!",
                message = "5분 후에 같이 시작해보자구",
                vibration = alarmInfo!!.isVibrationEnabled,
                notificationId = requestCode,
                smallIconRes = R.drawable.push_alarm_icon
            )

            // CallActivity 실행
            val callIntent = Intent(context, CallActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_SINGLE_TOP)
                putExtra("todoTask", alarmInfo.todo)
            }
            context.startActivity(callIntent)

            if(alarmInfo.isRepeating){
                val alarmType = AlarmType.fromRequestCode(requestCode)
                if (alarmType != null) {
                    AlarmScheduler.scheduleAlarm(
                        context = context,
                        alarmType = alarmType,
                        alarmInfo = alarmInfo
                    )
                } else {
                    Log.e("Alarm", "알 수 없는 requestCode: $requestCode")
                }
            }
        }
    }
}