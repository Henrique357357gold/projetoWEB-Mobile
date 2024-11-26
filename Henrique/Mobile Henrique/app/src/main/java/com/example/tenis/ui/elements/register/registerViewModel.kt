package com.example.tenis.ui.elements.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tenis.data.repositories.AuthRepository
import com.example.tenis.ui.elements.login.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class registerViewModel @Inject constructor(
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

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            val result = authRepository.register(email, password)
            _loginState.value = if (result.isSuccess) {
                Log.d("registerViewModel", "Registration successful")
                LoginState.Success
                } else {
                Log.d("registerViewModel", "Registration failed: ${result.exceptionOrNull()?.message}")
                LoginState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }

    }

}