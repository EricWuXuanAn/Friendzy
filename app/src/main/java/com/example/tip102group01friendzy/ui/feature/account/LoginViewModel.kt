package com.example.tip102group01friendzy.ui.feature.account

import android.content.Context
import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer

//VM :管理UI狀態及邏輯
class LoginViewModel(private val context: Context) : ViewModel() {
    private val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    private val repository = LoginRepository()

    var email = mutableStateOf(preferences.getString("saved_email","")?:"")
    var mpassword = mutableStateOf(preferences.getString("saved_password","")?:"")

    var isLoggedIn = mutableStateOf(preferences.getBoolean("is_logged_in",false))

    private val _loginState = MutableStateFlow<LoginResponse?>(null)
    val loginState :StateFlow<LoginResponse?> = _loginState.asStateFlow()

    suspend fun login() {
        if (email.value.isBlank() || mpassword.value.isBlank()) {
            _snackbarMessage.value = "empty_fields"
            return
        }


        val response = repository.login(email.value, mpassword.value)
        _loginState.value = response

        when(response){
            is LoginResponse.Success -> {
                if(response.result.statu){
                    saveLoginState()
                    isLoggedIn.value =true
                    _snackbarMessage.value = response.result.message
                }else{
                    _snackbarMessage.value = response.result.message
                }
            }
            is LoginResponse.Error -> {
                _snackbarMessage.value = response.message
            }
        }

    }

    private fun saveLoginState(){
        preferences.edit().apply{
            putString("saved_email", email.value)
            putString("saved_password", mpassword.value)
            putBoolean("is_logged_in", true)
            apply()
        }
    }

    val emailRegex = Patterns.EMAIL_ADDRESS
    val isValidEmail: Boolean
        get() = emailRegex.matcher(email.value).matches()

    private val _snackbarTrigger = MutableStateFlow(0)
    val snackbarTrigger: StateFlow<Int> = _snackbarTrigger.asStateFlow()

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage.asStateFlow()


    fun onLoginClicked() {
        if (email.value.isBlank() || mpassword.value.isBlank()) {
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
        mpassword.value = ""
    }
}