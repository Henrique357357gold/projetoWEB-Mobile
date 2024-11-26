package com.example.tenis.ui.elements.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tenis.data.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class loginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    init {
        checkUserLoggedIn()
    }

    private fun checkUserLoggedIn() {
        if (authRepository.isUserLoggedIn()) {
            _loginState.value = LoginState.Success
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            val result = authRepository.login(email, password)
            _loginState.value = if (result.isSuccess) {
                Log.d("loginViewModel", "Login successful")
                LoginState.Success
            } else {
                Log.d("loginViewModel", "Login failed: ${result.exceptionOrNull()?.message}")
                LoginState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }

}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}