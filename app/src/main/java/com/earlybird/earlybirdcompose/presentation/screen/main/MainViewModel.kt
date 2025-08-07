package com.earlybird.earlybirdcompose.presentation.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.earlybird.earlybirdcompose.data.entity.TodoEntity
import com.earlybird.earlybirdcompose.data.repository.TodoRepository
import com.earlybird.earlybirdcompose.presentation.screen.main.component.TodoItem
import com.earlybird.earlybirdcompose.presentation.screen.main.component.TodoStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()
    
    init {
        loadTodos()
    }
    
    private fun loadTodos() {
        viewModelScope.launch {
            todoRepository.getActiveTodos()
                .map { entities -> entities.map { it.toTodoItem() } }
                .collect { todoItems ->
                    _uiState.value = _uiState.value.copy(todoItems = todoItems)
                }
        }
    }
    
    fun addTodo(
        taskContent: String,
        timerDurationMinutes: Int? = null,
        reminderTime: Long? = null,
        hasCallReminder: Boolean = false,
        isRepeating: Boolean = false,
        repeatPattern: String? = null,
        hasVibration: Boolean = false,
        scheduledDate: Long? = null,
        onTodoCreated: ((Long) -> Unit)? = null // 생성된 todoId를 받기 위한 콜백
    ) {
        viewModelScope.launch {
            val todoId = todoRepository.createTodo(
                taskContent = taskContent,
                timerDurationMinutes = timerDurationMinutes,
                reminderTime = reminderTime,
                hasCallReminder = hasCallReminder,
                isRepeating = isRepeating,
                repeatPattern = repeatPattern,
                hasVibration = hasVibration,
                scheduledDate = scheduledDate
            )
            onTodoCreated?.invoke(todoId)
        }
    }
    
    fun markTodoAsCompleted(todoId: Int) {
        viewModelScope.launch {
            todoRepository.markAsCompleted(todoId)
        }
    }
    
    fun deleteTodo(todoId: Int) {
        viewModelScope.launch {
            todoRepository.deleteTodoById(todoId)
        }
    }
    
    fun updateMoodResponse(todoId: Int, mood: Int) {
        viewModelScope.launch {
            todoRepository.updateMoodResponse(todoId, mood)
        }
    }
    
    suspend fun getTodoById(todoId: Int) = todoRepository.getTodoById(todoId)
    
    // 데이터베이스 기반 상태 업데이트
    fun updateTodoStatus(todoId: Int, status: TodoStatus) {
        viewModelScope.launch {
            todoRepository.updateTodoStatus(todoId, status.ordinal)
        }
    }
}

data class MainUiState(
    val todoItems: List<TodoItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

// Extension function to convert TodoEntity to TodoItem
private fun TodoEntity.toTodoItem(): TodoItem {
    val statusFromDb = when (this.status) {
        1 -> TodoStatus.IN_PROGRESS
        2 -> TodoStatus.COMPLETED
        else -> TodoStatus.NOT_STARTED
    }
    
    return TodoItem(
        id = this.id,
        text = this.taskContent,
        reservedTime = this.reminderTime?.let { 
            // Convert timestamp to 12-hour format with AM/PM in English
            java.text.SimpleDateFormat("hh:mm a", java.util.Locale.ENGLISH)
                .format(java.util.Date(it))
        },
        timerDuration = this.timerDurationMinutes?.let { "${it}min" },
        hasTimer = this.timerDurationMinutes != null,
        hasCall = this.hasCallReminder,
        status = statusFromDb // 데이터베이스에서 가져온 상태 사용
    )
}