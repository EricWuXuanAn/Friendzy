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

//    private val _snackbarTrigger = MutableStateFlow(0)
//    val snackbarTrigger: StateFlow<Int> = _snackbarTrigger.asStateFlow()
//
//    private val _snackbarMessage = MutableStateFlow<String?>(null)
//    val snackbarMessage: StateFlow<String?> = _snackbarMessage.asStateFlow()

    private val _errorRequest = MutableStateFlow<List<String>>(emptyList())
    val errorRequest: StateFlow<List<String>> = _errorRequest.asStateFlow()

    private val _naviRequest = MutableStateFlow<Boolean?>(null)
    val naviRequest = _naviRequest.asStateFlow()

    fun onRegisterClicked() {
        if (account.value.isBlank() || password.value.isBlank() || confirmPassword.value.isBlank() || username.value.isBlank()) {
            //_snackbarMessage.value = "Field cannot be empty."
            //_snackbarTrigger.value += 1
            _errorRequest.update { currentList ->
                currentList + "Field cannot be empty."
            }
        }
        if (!isValidEmail) {
            //_snackbarMessage.value = "Email Formatting Error."
            //_snackbarTrigger.value += 1
            _errorRequest.update { currentList ->
                currentList + "Email Formatting Error."
            }
        }
        if (password.value.isNotBlank() && password.value.count() < 8) {
            //_snackbarMessage.value = "Password(at least 8 characters)"
            //_snackbarTrigger.value += 1
            _errorRequest.update { currentList ->
                currentList + "Password(at least 8 characters)"
            }
        }
        if (password.value != confirmPassword.value) {
            //_snackbarMessage.value = "Password do not match."
            //_snackbarTrigger.value += 1
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