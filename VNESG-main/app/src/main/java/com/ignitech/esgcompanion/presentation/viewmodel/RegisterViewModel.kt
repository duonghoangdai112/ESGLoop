package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.domain.entity.UserRole
import com.ignitech.esgcompanion.domain.repository.AuthRepository
import com.ignitech.esgcompanion.util.ValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    
    private val _currentUser = MutableStateFlow<com.ignitech.esgcompanion.domain.entity.User?>(null)
    val currentUser: StateFlow<com.ignitech.esgcompanion.domain.entity.User?> = _currentUser.asStateFlow()
    
    data class UiState(
        val name: String = "",
        val email: String = "",
        val password: String = "",
        val confirmPassword: String = "",
        val selectedRole: UserRole? = null,
        val nameError: String = "",
        val emailError: String = "",
        val passwordError: String = "",
        val confirmPasswordError: String = "",
        val roleError: String = "",
        val isPasswordVisible: Boolean = false,
        val isConfirmPasswordVisible: Boolean = false,
        val isRoleMenuExpanded: Boolean = false,
        val isLoading: Boolean = false,
        val error: String? = null
    )
    
    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(
            name = name, 
            nameError = if (name.isNotEmpty() && !ValidationUtils.isValidName(name)) "Name must be at least 2 characters" else "",
            error = null
        )
    }
    
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
    
    fun updateConfirmPassword(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(
            confirmPassword = confirmPassword, 
            confirmPasswordError = if (confirmPassword.isNotEmpty() && confirmPassword != _uiState.value.password) "Passwords do not match" else "",
            error = null
        )
    }
    
    fun updateRole(role: UserRole) {
        _uiState.value = _uiState.value.copy(selectedRole = role, roleError = "")
    }
    
    fun togglePasswordVisibility() {
        _uiState.value = _uiState.value.copy(isPasswordVisible = !_uiState.value.isPasswordVisible)
    }
    
    fun toggleConfirmPasswordVisibility() {
        _uiState.value = _uiState.value.copy(isConfirmPasswordVisible = !_uiState.value.isConfirmPasswordVisible)
    }
    
    fun toggleRoleMenu() {
        _uiState.value = _uiState.value.copy(isRoleMenuExpanded = !_uiState.value.isRoleMenuExpanded)
    }
    
    fun register() {
        val currentState = _uiState.value
        
        // Validation
        if (!ValidationUtils.isValidName(currentState.name)) {
            _uiState.value = currentState.copy(nameError = "Name must be at least 2 characters")
            return
        }
        
        if (!ValidationUtils.isValidEmail(currentState.email)) {
            _uiState.value = currentState.copy(emailError = "Invalid email")
            return
        }
        
        if (!ValidationUtils.isValidPassword(currentState.password)) {
            _uiState.value = currentState.copy(passwordError = "Password must be at least 6 characters")
            return
        }
        
        if (currentState.password != currentState.confirmPassword) {
            _uiState.value = currentState.copy(confirmPasswordError = "Passwords do not match")
            return
        }
        
        if (currentState.selectedRole == null) {
            _uiState.value = currentState.copy(roleError = "Please select an account type")
            return
        }
        
        _uiState.value = currentState.copy(
            isLoading = true, 
            nameError = "", 
            emailError = "", 
            passwordError = "", 
            confirmPasswordError = "", 
            roleError = "", 
            error = null
        )
        
        viewModelScope.launch {
            authRepository.register(
                currentState.email,
                currentState.password,
                currentState.name,
                currentState.selectedRole!!
            ).collect { result ->
                when (result) {
                    is com.ignitech.esgcompanion.common.Result.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is com.ignitech.esgcompanion.common.Result.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = if (result.data == null) "Email already in use" else null
                        )
                        _currentUser.value = result.data
                    }
                    is com.ignitech.esgcompanion.common.Result.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = result.exception.message ?: "Registration failed"
                        )
                    }
                }
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(
            error = null, 
            nameError = "", 
            emailError = "", 
            passwordError = "", 
            confirmPasswordError = "", 
            roleError = ""
        )
    }
}

