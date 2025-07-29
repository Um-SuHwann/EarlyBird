package com.earlybird.earlybirdcompose.presentation.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.earlybird.earlybirdcompose.data.entity.TodoEntity
import com.earlybird.earlybirdcompose.data.repository.TodoRepository
import com.earlybird.earlybirdcompose.presentation.screen.main.component.TodoItem
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
        scheduledDate: Long? = null
    ) {
        viewModelScope.launch {
            todoRepository.createTodo(
                taskContent = taskContent,
                timerDurationMinutes = timerDurationMinutes,
                reminderTime = reminderTime,
                hasCallReminder = hasCallReminder,
                isRepeating = isRepeating,
                repeatPattern = repeatPattern,
                hasVibration = hasVibration,
                scheduledDate = scheduledDate
            )
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
}

data class MainUiState(
    val todoItems: List<TodoItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

// Extension function to convert TodoEntity to TodoItem
private fun TodoEntity.toTodoItem(): TodoItem {
    return TodoItem(
        id = this.id,
        text = this.taskContent,
        reservedTime = this.reminderTime?.let { 
            // Convert timestamp to readable time format
            java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
                .format(java.util.Date(it))
        },
        timerDuration = this.timerDurationMinutes?.let { "${it}min" },
        hasTimer = this.timerDurationMinutes != null,
        hasCall = this.hasCallReminder
    )
}