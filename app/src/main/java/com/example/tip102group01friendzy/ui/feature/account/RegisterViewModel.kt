package com.example.tip102group01friendzy.ui.feature.account

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegisterViewModel : ViewModel() {
    var account = mutableStateOf("")
    var password = mutableStateOf("")
    var confirmPassword = mutableStateOf("")
    var username = mutableStateOf("")
    var status = mutableStateOf(false)

    val emailRegex = Patterns.EMAIL_ADDRESS
    val isValidEmail: Boolean
        get() = emailRegex.matcher(account.value).matches()

    private val _snackbarTrigger = MutableStateFlow(0)
    val snackbarTrigger: StateFlow<Int> = _snackbarTrigger.asStateFlow()

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage.asStateFlow()

    fun onRegesterClicked() {
        if (account.value.isBlank() || password.value.isBlank() || confirmPassword.value.isBlank() || username.value.isBlank()) {
            _snackbarMessage.value = "Field cannot be empty."
            _snackbarTrigger.value += 1
        } else if (!isValidEmail) {
            _snackbarMessage.value = "Email Formatting Error."
            _snackbarTrigger.value += 1
        } else if (password.value.isNotBlank() && password.value.count() < 8) {
            _snackbarMessage.value = "Password(at least 8 characters)"
            _snackbarTrigger.value += 1
        } else if (password.value != confirmPassword.value) {
            _snackbarMessage.value = "Password do not match."
            _snackbarTrigger.value += 1
        } else {
            //TODO: 確認之後看DB是否註冊過，若註冊過要跳通知
            _snackbarMessage.value = null
        }
    }
}