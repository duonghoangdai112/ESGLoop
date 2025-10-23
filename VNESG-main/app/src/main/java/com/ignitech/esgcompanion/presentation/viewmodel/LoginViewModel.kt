package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.domain.repository.AuthRepository
import com.ignitech.esgcompanion.util.ValidationUtils
import com.ignitech.esgcompanion.data.local.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    
    private val _currentUser = MutableStateFlow<com.ignitech.esgcompanion.domain.entity.User?>(null)
    val currentUser: StateFlow<com.ignitech.esgcompanion.domain.entity.User?> = _currentUser.asStateFlow()
    
    init {
        loadSavedCredentials()
    }
    
    private fun loadSavedCredentials() {
        if (preferencesManager.getRememberMe()) {
            val savedEmail = preferencesManager.getSavedEmail()
            val savedPassword = preferencesManager.getSavedPassword()
            
            if (savedEmail != null && savedPassword != null) {
                _uiState.value = _uiState.value.copy(
                    email = savedEmail,
                    password = savedPassword,
                    rememberMe = true
                )
            }
        }
    }
    
    data class UiState(
        val email: String = "",
        val password: String = "",
        val emailError: String = "",
        val passwordError: String = "",
        val isPasswordVisible: Boolean = false,
        val rememberMe: Boolean = false,
        val isLoading: Boolean = false,
        val error: String? = null,
        val isLoggedIn: Boolean = false
    )
    
    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(
            email = email, 
            emailError = if (email.isNotEmpty() && !ValidationUtils.isValidEmail(email)) "Invalid email" else "",
            error = null
        )
    }
    
    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password, 
            passwordError = if (password.isNotEmpty() && !ValidationUtils.isValidPassword(password)) "Password must be at least 6 characters" else "",
            error = null
        )
    }
    
    fun login() {
        val currentState = _uiState.value
        
        // Validation
        if (!ValidationUtils.isValidEmail(currentState.email)) {
            _uiState.value = currentState.copy(emailError = "Invalid email")
            return
        }
        
        if (!ValidationUtils.isValidPassword(currentState.password)) {
            _uiState.value = currentState.copy(passwordError = "Password must be at least 6 characters")
            return
        }
        
        _uiState.value = currentState.copy(isLoading = true, emailError = "", passwordError = "", error = null)
        
        viewModelScope.launch {
            authRepository.login(currentState.email, currentState.password).collect { result ->
                when (result) {
                    is com.ignitech.esgcompanion.common.Result.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is com.ignitech.esgcompanion.common.Result.Success -> {
                        // Login successful
                        android.util.Log.d("LoginViewModel", "Login successful for user: ${result.data?.name}")
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            isLoggedIn = true,
                            error = null
                        )
                        _currentUser.value = result.data
                        
                        // Handle remember me
                        if (currentState.rememberMe) {
                            preferencesManager.setRememberMe(true)
                            preferencesManager.setSavedCredentials(currentState.email, currentState.password)
                        } else {
                            preferencesManager.setRememberMe(false)
                            preferencesManager.clearSavedCredentials()
                        }
                    }
                    is com.ignitech.esgcompanion.common.Result.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = result.exception.message ?: "Login failed"
                        )
                    }
                }
            }
        }
    }
    
    fun togglePasswordVisibility() {
        _uiState.value = _uiState.value.copy(isPasswordVisible = !_uiState.value.isPasswordVisible)
    }
    
    fun updateRememberMe(rememberMe: Boolean) {
        _uiState.value = _uiState.value.copy(rememberMe = rememberMe)
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null, emailError = "", passwordError = "")
    }
}

