package com.pitchpulse.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    object Success : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AuthRepository(application.applicationContext)

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _uiState.value = AuthUiState.Error("Please enter both email and password.")
            return
        }
        _uiState.value = AuthUiState.Loading
        viewModelScope.launch {
            when (val result = repository.login(email, password)) {
                is AuthResult.Success -> _uiState.value = AuthUiState.Success
                is AuthResult.Error -> _uiState.value = AuthUiState.Error(result.message)
            }
        }
    }

    fun register(email: String, password: String, displayName: String?) {
        if (email.isBlank() || password.isBlank()) {
            _uiState.value = AuthUiState.Error("Please enter both email and password.")
            return
        }
        _uiState.value = AuthUiState.Loading
        viewModelScope.launch {
            when (val result = repository.register(email, password, displayName)) {
                is AuthResult.Success -> _uiState.value = AuthUiState.Success
                is AuthResult.Error -> _uiState.value = AuthUiState.Error(result.message)
            }
        }
    }
}
