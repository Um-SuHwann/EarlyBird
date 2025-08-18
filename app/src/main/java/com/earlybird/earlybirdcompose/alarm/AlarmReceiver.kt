package com.earlybird.earlybirdcompose.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.earlybird.earlybirdcompose.R
import com.earlybird.earlybirdcompose.data.database.TodoDatabase
import com.earlybird.earlybirdcompose.presentation.screen.call.CallActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//예약 시간이 되면 호출되는 BroadcastReceiver

class  AlarmReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context, intent: Intent) {
        val requestCode : Int = intent.getIntExtra("requestCode", -1)

        // 새로운 데이터베이스 기반 알람 처리
        val todoId = intent.getIntExtra("todoId", -1)
        if (todoId != -1) {
            handleDatabaseAlarm(context, todoId, requestCode)
        } else {
            Log.e("Alarm", "알 수 없는 알람 유형 - requestCode: $requestCode")
        }
    }
    
    /**
     * 데이터베이스 기반 알람 처리 메소드
     * 
     * @param context Android Context
     * @param todoId 데이터베이스의 할일 ID
     * @param requestCode 알람의 고유 식별자
     * 
     * 동작 순서:
     * 1. 데이터베이스에서 todoId로 할일 정보 조회
     * 2. 할일이 존재하면 푸시 알림 표시 
     * 3. CallActivity 실행하여 집중 세션 시작
     * 4. 반복 알람이면 다음 알람 자동 예약
     */
    private fun handleDatabaseAlarm(context: Context, todoId: Int, requestCode: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // 데이터베이스 인스턴스 가져오기
                val database = TodoDatabase.getDatabase(context)
                val todoDao = database.todoDao()
                
                // todoId로 해당 할일 조회
                val todo = todoDao.getTodoById(todoId)
                
                if (todo != null) {
                    // 1. 푸시 알림 표시 (사용자에게 알람 발생을 알림)
                    NotificationHelper.showNotification(
                        context = context,
                        title = "5 minute left!",
                        message = "Let's start together in 5 minutes!",
                        vibration = todo.hasVibration, // 데이터베이스의 진동 설정 사용
                        notificationId = requestCode,  // 고유한 알림 ID
                        smallIconRes = R.drawable.push_alarm_icon
                    )
                    // 2. CallActivity 실행 (집중 세션 화면으로 전환)
                    val callIntent = Intent(context, CallActivity::class.java).apply {
                        // 새로운 태스크로 실행하여 다른 앱 위에 표시
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or
                                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        // 데이터베이스에서 가져온 실제 할일 내용 전달
                        putExtra("todo_task", todo.taskContent)
                        // 집중 시간 전달 (기본값 2분)
                        putExtra("timer_duration", todo.timerDurationMinutes ?: 2)
                    }
                    context.startActivity(callIntent)

                    // 수정해야 되는 부분임 -> 아직 반복 설정 기능이 없고, 만약에 생긴다고 해도 문제가 발생 -> 다음날 맞춰야 되는데 당일날 맞춰서 계속 알람 실행되는 문제 발생
                    if (todo.isRepeating) {
                        Log.d("Alarm", "반복 알람 재예약 - todoId: $todoId")
                        AlarmScheduler.scheduleAlarmFromTodo(context, todo)
                    }
                } else {
                    // 할일이 삭제되었거나 존재하지 않는 경우
                    Log.e("Alarm", "할일을 찾을 수 없음 - todoId: $todoId")
                }
            } catch (e: Exception) {
                // 데이터베이스 접근 오류 처리
                Log.e("Alarm", "데이터베이스 알람 처리 중 오류 - todoId: $todoId", e)
            }
        }
    }
}