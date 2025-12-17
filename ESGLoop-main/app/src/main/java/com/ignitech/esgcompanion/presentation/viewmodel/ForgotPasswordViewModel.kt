package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.util.ValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor() : ViewModel() {
    
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    
    data class UiState(
        val email: String = "",
        val emailError: String = "",
        val isLoading: Boolean = false,
        val error: String? = null,
        val success: String? = null
    )
    
    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(
            email = email, 
            emailError = if (email.isNotEmpty() && !ValidationUtils.isValidEmail(email)) "Email không hợp lệ" else "",
            error = null
        )
    }
    
    fun resetPassword() {
        val currentState = _uiState.value
        
        // Validation
        if (!ValidationUtils.isValidEmail(currentState.email)) {
            _uiState.value = currentState.copy(emailError = "Email không hợp lệ")
            return
        }
        
        _uiState.value = currentState.copy(isLoading = true, emailError = "", error = null)
        
        viewModelScope.launch {
            // Simulate API call
            kotlinx.coroutines.delay(2000)
            
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                success = "Hướng dẫn đặt lại mật khẩu đã được gửi đến email của bạn"
            )
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null, emailError = "")
    }
    
    fun clearSuccess() {
        _uiState.value = _uiState.value.copy(success = null)
    }
}

