package com.example.tip102group01friendzy.ui.feature.account

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel : ViewModel() {
    var account = mutableStateOf("")
    var password = mutableStateOf("")
    var confirmPassword = mutableStateOf("")
    var username = mutableStateOf("")

    val emailRegex = Patterns.EMAIL_ADDRESS
    val isValidEmail: Boolean
        get() = emailRegex.matcher(account.value).matches()

    private val _errorRequest = MutableStateFlow<List<String>>(emptyList())
    val errorRequest: StateFlow<List<String>> = _errorRequest.asStateFlow()

    private val _naviRequest = MutableStateFlow<Boolean?>(null)
    val naviRequest = _naviRequest.asStateFlow()

    fun onRegisterClicked() {
        if (account.value.isBlank() || password.value.isBlank() || confirmPassword.value.isBlank() || username.value.isBlank()) {
            _errorRequest.update { currentList ->
                currentList + "Field cannot be empty."
            }
        }
        if (!isValidEmail) {
            _errorRequest.update { currentList ->
                currentList + "Email Formatting Error."
            }
        }
        if (password.value.isNotBlank() && password.value.count() < 8) {
            _errorRequest.update { currentList ->
                currentList + "Password(at least 8 characters)"
            }
        }
        if (password.value != confirmPassword.value) {
            _errorRequest.update { currentList ->
                currentList + "Password do not match."
            }
        }
        if (!_errorRequest.value.any()){
            _naviRequest.update { true }
        }
    }

    fun consumeErrorRequest() {
        _errorRequest.update { emptyList() }
    }

    fun consumeNaviRequest() {
        _naviRequest.update { null }
    }
}