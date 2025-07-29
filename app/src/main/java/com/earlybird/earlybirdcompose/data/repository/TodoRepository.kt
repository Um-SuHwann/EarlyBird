package com.earlybird.earlybirdcompose.data.repository

import com.earlybird.earlybirdcompose.data.dao.TodoDao
import com.earlybird.earlybirdcompose.data.entity.TodoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoRepository @Inject constructor(
    private val todoDao: TodoDao
) {
    
    fun getAllTodos(): Flow<List<TodoEntity>> = todoDao.getAllTodos()
    
    fun getActiveTodos(): Flow<List<TodoEntity>> = todoDao.getActiveTodos()
    
    fun getCompletedTodos(): Flow<List<TodoEntity>> = todoDao.getCompletedTodos()
    
    suspend fun getTodoById(id: Int): TodoEntity? = todoDao.getTodoById(id)
    
    fun getUpcomingReminders(currentTime: Long): Flow<List<TodoEntity>> = 
        todoDao.getUpcomingReminders(currentTime)
    
    suspend fun insertTodo(todo: TodoEntity): Long = todoDao.insertTodo(todo)
    
    suspend fun updateTodo(todo: TodoEntity) = todoDao.updateTodo(todo)
    
    suspend fun deleteTodo(todo: TodoEntity) = todoDao.deleteTodo(todo)
    
    suspend fun deleteTodoById(id: Int) = todoDao.deleteTodoById(id)
    
    suspend fun markAsCompleted(id: Int, completedAt: Long = System.currentTimeMillis()) = 
        todoDao.markAsCompleted(id, completedAt)
    
    suspend fun updateMoodResponse(id: Int, mood: Int) = 
        todoDao.updateMoodResponse(id, mood)
    
    suspend fun deleteCompletedTodos() = todoDao.deleteCompletedTodos()
    
    // 편의 메서드들
    suspend fun createTodo(
        taskContent: String,
        timerDurationMinutes: Int? = null,
        reminderTime: Long? = null,
        hasCallReminder: Boolean = false,
        isRepeating: Boolean = false,
        repeatPattern: String? = null,
        hasVibration: Boolean = false,
        scheduledDate: Long? = null
    ): Long {
        val todo = TodoEntity(
            taskContent = taskContent,
            timerDurationMinutes = timerDurationMinutes,
            reminderTime = reminderTime,
            hasCallReminder = hasCallReminder,
            isRepeating = isRepeating,
            repeatPattern = repeatPattern,
            hasVibration = hasVibration,
            scheduledDate = scheduledDate
        )
        return insertTodo(todo)
    }
}