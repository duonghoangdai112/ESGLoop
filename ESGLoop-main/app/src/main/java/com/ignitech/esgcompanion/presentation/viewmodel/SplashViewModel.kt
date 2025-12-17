package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.domain.entity.User
import com.ignitech.esgcompanion.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()
    
    init {
        checkAuthStatus()
    }
    
    private fun checkAuthStatus() {
        viewModelScope.launch {
            android.util.Log.d("SplashViewModel", "Starting auth check")
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            authRepository.getCurrentUser().collect { result ->
                android.util.Log.d("SplashViewModel", "Auth result: $result")
                when (result) {
                    is com.ignitech.esgcompanion.common.Result.Loading -> {
                        android.util.Log.d("SplashViewModel", "Auth loading")
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is com.ignitech.esgcompanion.common.Result.Success -> {
                        android.util.Log.d("SplashViewModel", "Auth success, user: ${result.data}")
                        if (result.data != null) {
                            android.util.Log.d("SplashViewModel", "User found, setting as logged in")
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                isLoggedIn = true,
                                currentUser = result.data
                            )
                        } else {
                            android.util.Log.d("SplashViewModel", "No user found, not logged in")
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                isLoggedIn = false,
                                currentUser = null
                            )
                        }
                    }
                    is com.ignitech.esgcompanion.common.Result.Error -> {
                        android.util.Log.d("SplashViewModel", "Auth error: ${result.exception.message}")
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            isLoggedIn = false,
                            error = result.exception.message
                        )
                    }
                }
            }
        }
    }
}

data class SplashUiState(
    val isLoading: Boolean = true,
    val isLoggedIn: Boolean = false,
    val currentUser: User? = null,
    val error: String? = null
)
