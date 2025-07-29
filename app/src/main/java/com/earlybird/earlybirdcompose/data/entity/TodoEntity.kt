package com.earlybird.earlybirdcompose.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    // 기분 응답 (할일 완료 후)
    val moodResponse: Int? = null, // 1-5 점수나 enum 값
    
    // 할 일 내용
    val taskContent: String,
    
    // 타이머 시간 (분 단위)
    val timerDurationMinutes: Int? = null,
    
    // 리마인더 시간 (timestamp)
    val reminderTime: Long? = null,
    
    // 리마인더 시간에 전화 여부
    val hasCallReminder: Boolean = false,
    
    // 반복 여부 및 패턴
    val isRepeating: Boolean = false,
    val repeatPattern: String? = null, // "daily", "weekly", "monthly" 등
    
    // 진동 여부
    val hasVibration: Boolean = false,
    
    // 생성 시간
    val createdAt: Long = System.currentTimeMillis(),
    
    // 알림이 울릴 날짜 (향후 사용)
    val scheduledDate: Long? = null,
    
    // 추가 상태 필드들
    val isCompleted: Boolean = false,
    val completedAt: Long? = null
)