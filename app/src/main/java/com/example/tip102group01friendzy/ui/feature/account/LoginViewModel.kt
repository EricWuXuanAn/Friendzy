package com.example.tip102group01friendzy.ui.feature.account

import android.content.Context
import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

//VM :管理UI狀態及邏輯
class LoginViewModel(private val context: Context) : ViewModel() {
    private val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    private val repository = LoginRepository()

    var email = mutableStateOf(preferences.getString("saved_email", "") ?: "")
    var mpassword = mutableStateOf(preferences.getString("saved_password", "") ?: "")

//    var token = mutableStateOf(preferences.getString("session_token", "") ?: "")
//
//    var isLoggedIn = mutableStateOf(preferences.getBoolean("is_logged_in",false))

    private val _loginState = MutableStateFlow<MemberInfo?>(null)
    val loginState: StateFlow<MemberInfo?> = _loginState.asStateFlow()

    suspend fun login() {
        if (email.value.isBlank() || mpassword.value.isBlank()) {
            _snackbarMessage.value = "empty_fields"
            _snackbarTrigger.value += 1
            return
        }

        val memberInfo = repository.login(email.value, mpassword.value)
        if (memberInfo != null) {
            _loginState.update { memberInfo }
            onLoginClicked()
            // user 資料儲存
            saveMemberInfoToPreferences(memberInfo)
            _snackbarMessage.value = "Login success"
            _snackbarTrigger.value +=1
        } else {
            _snackbarMessage.value = "使用者帳號或密碼錯誤"
            _snackbarTrigger.value += 1
        }
    }

    private fun saveMemberInfoToPreferences(memberInfo: MemberInfo){
        with(preferences.edit()){
            putString("email",memberInfo.email)
            putString("password",memberInfo.mpassword)
            putString("member_name",memberInfo.member_name)
            putInt("member_no",memberInfo.member_no)
            putString("member_nick_name",memberInfo.member_nick_name)
            putString("phone",memberInfo.phone)
            putString("introduction",memberInfo.introduction)
            putBoolean("member_status",memberInfo.member_status)
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