package com.example.tip102group01friendzy.ui.feature.account

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.example.tip102group01friendzy.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

//VM :管理UI狀態及邏輯
class LoginViewModel : ViewModel() {
    var account = mutableStateOf("")
    var password = mutableStateOf("")

    val emailRegex = Patterns.EMAIL_ADDRESS
    val isValidEmail: Boolean
        get() = emailRegex.matcher(account.value).matches()

    private val _snackbarTrigger = MutableStateFlow(0)
    val snackbarTrigger: StateFlow<Int> = _snackbarTrigger.asStateFlow()

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage.asStateFlow()


    fun onLoginClicked() {
        if (account.value.isBlank() || password.value.isBlank()) {
            _snackbarMessage.value = "empty_fields"
            _snackbarTrigger.value += 1
        } else if (!isValidEmail) {
            _snackbarMessage.value = "invalid_email"
            _snackbarTrigger.value += 1
        } else {
            //TODO: 處理登入邏輯 -> 曾經註冊過且帳密正確即可登入，帳密不正確跳通知
            _snackbarMessage.value = null
        }
    }

    fun clearPassword() {
        password.value = ""
    }
}