package com.earlybird.earlybirdcompose.alarm

enum class AlarmType(
    val requestCode: Int,
    val defaultHour: Int,
    val defaultMinute: Int,
    val defaultPa: String,
    val vibration: Boolean
)
{
    MORNING(1001, 9, 0, "AM", true),
    NIGHT(1002, 9, 0, "PM", true),
    USER(1003, 7, 30, "AM", true); // 기본값, 실제 사용자가 바꿀 수 있음

    companion object {
        const val MORNING_REQUEST_CODE = 1001
        const val NIGHT_REQUEST_CODE = 1002
        const val USER_REQUEST_CODE = 1003
        
        // 데이터베이스 기반 동적 requestCode 생성
        fun generateRequestCode(todoId: Int): Int {
            return 10000 + todoId // todoId를 기반으로 고유한 requestCode 생성
        }
        
        fun fromRequestCode(code: Int): AlarmType? {
            return when {
                code == MORNING_REQUEST_CODE -> MORNING
                code == NIGHT_REQUEST_CODE -> NIGHT
                code == USER_REQUEST_CODE -> USER
                code >= 10000 -> USER // 동적으로 생성된 requestCode는 USER 타입으로 처리
                else -> null
            }
        }
        
        fun extractTodoId(requestCode: Int): Int? {
            return if (requestCode >= 10000) requestCode - 10000 else null
        }
    }
}