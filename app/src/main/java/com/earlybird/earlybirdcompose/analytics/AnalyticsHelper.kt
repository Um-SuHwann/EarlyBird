package com.earlybird.earlybirdcompose.analytics

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnalyticsHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val firebaseAnalytics: FirebaseAnalytics? by lazy { 
        try {
            //이게 firebase 객체 선어한다고 생각하면 된다.
            Log.d("AnalyticsHelper", "Attempting to initialize Firebase Analytics")
            Firebase.analytics
        } catch (e: Exception) {
            Log.e("AnalyticsHelper", "Failed to initialize Firebase Analytics", e)
            null
        }
    }
    
    private fun safeLogEvent(eventName: String, bundle: Bundle? = null) {
        try {
            firebaseAnalytics?.logEvent(eventName, bundle ?: Bundle())
        } catch (e: Exception) {
            Log.e("🔥 ANALYTICS", "❌ Failed to log event: $eventName", e)
        }
    }

    // 스크린 뷰 트래킹
    fun logScreenView(screenName: String, screenClass: String) {
        Log.d("🔥 ANALYTICS", "📱 SCREEN VIEW: $screenName ($screenClass)")
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenClass)
        }
        safeLogEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

    // Todo 생성 이벤트
    fun logTodoCreated(hasCall: Boolean) {
        Log.d("🔥 ANALYTICS", "✅ TODO CREATED: hasCall=$hasCall")
        val bundle = Bundle().apply {
            putBoolean("has_call", hasCall)
        }
        safeLogEvent("todo_created", bundle)
    }

    // Todo 완료 이벤트(start -> Done -> 체크표시까지 완료되었을때)
    fun logTodoCompleted(todoId: Int, completionTime: Long) {
        val bundle = Bundle().apply {
            putInt("todo_id", todoId)
            putLong("completion_time_ms", completionTime)
        }
        firebaseAnalytics?.logEvent("todo_completed", bundle)
    }

    // 알람 설정 이벤트
    fun logAlarmSet(timeUntilAlarm: Long) {
        val bundle = Bundle().apply {
            putLong("time_until_alarm_ms", timeUntilAlarm)
        }
        firebaseAnalytics?.logEvent("alarm_set", bundle)
    }

    // 타이머 시작 이벤트
    fun logTimerStarted(durationMinutes: Int) {
        val bundle = Bundle().apply {
            putInt("duration_minutes", durationMinutes)
        }
        firebaseAnalytics?.logEvent("timer_started", bundle)
    }

    // Day Streak 이벤트
    fun logDayStreakUpdated(streakCount: Int, isNewRecord: Boolean) {
        val bundle = Bundle().apply {
            putInt("streak_count", streakCount)
            putBoolean("is_new_record", isNewRecord)
        }
        firebaseAnalytics?.logEvent("day_streak_updated", bundle)
    }

    // 모드 전환 이벤트
    fun logModeToggle(fromMode: String, toMode: String) {
        val bundle = Bundle().apply {
            putString("from_mode", fromMode)
            putString("to_mode", toMode)
        }
        firebaseAnalytics?.logEvent("mode_toggled", bundle)
    }
}