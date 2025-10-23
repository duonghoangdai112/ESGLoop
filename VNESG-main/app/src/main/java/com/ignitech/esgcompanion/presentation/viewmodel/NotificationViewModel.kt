package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.domain.entity.Notification
import com.ignitech.esgcompanion.domain.entity.NotificationType
import com.ignitech.esgcompanion.domain.repository.NotificationRepository
import com.ignitech.esgcompanion.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(NotificationUiState())
    val uiState: StateFlow<NotificationUiState> = _uiState.asStateFlow()
    
    private val _currentUserId = MutableStateFlow<String?>(null)
    
    init {
        getCurrentUser()
    }
    
    private fun getCurrentUser() {
        viewModelScope.launch {
            authRepository.getCurrentUser().collect { result ->
                when (result) {
                    is com.ignitech.esgcompanion.common.Result.Success -> {
                        result.data?.let { user ->
                            _currentUserId.value = user.id
                            loadNotifications(user.id)
                        }
                    }
                    else -> {}
                }
            }
        }
    }
    
    fun loadNotifications(userId: String? = null) {
        val targetUserId = userId ?: _currentUserId.value
        targetUserId?.let { id ->
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isLoading = true)
                
                notificationRepository.getNotificationsByUserId(id).collect { result ->
                    when (result) {
                        is com.ignitech.esgcompanion.common.Result.Loading -> {
                            _uiState.value = _uiState.value.copy(isLoading = true)
                        }
                        is com.ignitech.esgcompanion.common.Result.Success -> {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                notifications = result.data,
                                error = null
                            )
                        }
                        is com.ignitech.esgcompanion.common.Result.Error -> {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                error = result.exception.message
                            )
                        }
                    }
                }
            }
        }
    }
    
    fun loadUnreadNotifications() {
        _currentUserId.value?.let { userId ->
            viewModelScope.launch {
                notificationRepository.getUnreadNotificationsByUserId(userId).collect { result ->
                    when (result) {
                        is com.ignitech.esgcompanion.common.Result.Success -> {
                            _uiState.value = _uiState.value.copy(unreadNotifications = result.data)
                        }
                        else -> {}
                    }
                }
            }
        }
    }
    
    fun markAsRead(notificationId: String) {
        viewModelScope.launch {
            notificationRepository.markAsRead(notificationId).let { result ->
                when (result) {
                    is com.ignitech.esgcompanion.common.Result.Success -> {
                        // Refresh notifications
                        loadNotifications()
                    }
                    is com.ignitech.esgcompanion.common.Result.Error -> {
                        _uiState.value = _uiState.value.copy(error = result.exception.message)
                    }
                    else -> {}
                }
            }
        }
    }
    
    fun markAllAsRead() {
        _currentUserId.value?.let { userId ->
            viewModelScope.launch {
                notificationRepository.markAllAsRead(userId).let { result ->
                    when (result) {
                        is com.ignitech.esgcompanion.common.Result.Success -> {
                            // Refresh notifications
                            loadNotifications()
                        }
                        is com.ignitech.esgcompanion.common.Result.Error -> {
                            _uiState.value = _uiState.value.copy(error = result.exception.message)
                        }
                        else -> {}
                    }
                }
            }
        }
    }
    
    fun deleteNotification(notificationId: String) {
        viewModelScope.launch {
            notificationRepository.deleteNotification(notificationId).let { result ->
                when (result) {
                    is com.ignitech.esgcompanion.common.Result.Success -> {
                        // Refresh notifications
                        loadNotifications()
                    }
                    is com.ignitech.esgcompanion.common.Result.Error -> {
                        _uiState.value = _uiState.value.copy(error = result.exception.message)
                    }
                    else -> {}
                }
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
    
}

data class NotificationUiState(
    val isLoading: Boolean = false,
    val notifications: List<Notification> = emptyList(),
    val unreadNotifications: List<Notification> = emptyList(),
    val error: String? = null
)
