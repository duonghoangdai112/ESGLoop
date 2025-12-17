package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.domain.entity.User
import com.ignitech.esgcompanion.domain.repository.UserRepository
import com.ignitech.esgcompanion.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()
    
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()
    
    init {
        getCurrentUser()
    }
    
    private fun getCurrentUser() {
        viewModelScope.launch {
            authRepository.getCurrentUser().collect { result ->
                when (result) {
                    is com.ignitech.esgcompanion.common.Result.Success -> {
                        _currentUser.value = result.data
                    }
                    else -> {}
                }
            }
        }
    }
    
    fun getUsers() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            userRepository.getUsers().collect { result ->
                when (result) {
                    is com.ignitech.esgcompanion.common.Result.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is com.ignitech.esgcompanion.common.Result.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            users = result.data
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
    
    fun searchUsers(query: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            userRepository.searchUsers(query).collect { result ->
                when (result) {
                    is com.ignitech.esgcompanion.common.Result.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is com.ignitech.esgcompanion.common.Result.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            users = result.data
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
    
    fun getUserById(id: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            userRepository.getUserById(id).collect { result ->
                when (result) {
                    is com.ignitech.esgcompanion.common.Result.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is com.ignitech.esgcompanion.common.Result.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            selectedUser = result.data
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
    
    fun updateUser(user: User) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            val result = userRepository.updateUser(user)
            when (result) {
                is com.ignitech.esgcompanion.common.Result.Success -> {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    // Refresh users list
                    getUsers()
                }
                is com.ignitech.esgcompanion.common.Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.exception.message
                    )
                }
                else -> {}
            }
        }
    }
    
    fun deleteUser(id: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            val result = userRepository.deleteUser(id)
            when (result) {
                is com.ignitech.esgcompanion.common.Result.Success -> {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    // Refresh users list
                    getUsers()
                }
                is com.ignitech.esgcompanion.common.Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.exception.message
                    )
                }
                else -> {}
            }
        }
    }
    
    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _currentUser.value = null
        }
    }
}

data class UserUiState(
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
    val selectedUser: User? = null,
    val error: String? = null
)
