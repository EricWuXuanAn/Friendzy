package com.example.tip102group01friendzy.ui.feature.account

import android.content.Context
import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//VM :管理UI狀態及邏輯
// todo 不建議把 LoginViewModel 中直接使用 context ，然後如果沒有要持續使用物件，避免用 val
class LoginViewModel(context: Context) : ViewModel() {

    private var context:Context? = null
    private val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    private val repository = LoginRepository()


    // todo mutableStateOf 跟 MutableStateFlow 是完全不一樣的東西
    // todo 可以宣告一個 Constant 把 SharePreference 的 Key 抽出來，避免誤植
    var email = mutableStateOf(preferences.getString("email", "") ?: "")
    var mpassword = mutableStateOf(preferences.getString("password", "") ?: "")

    private val _loginState = MutableStateFlow<MemberInfo?>(null)
    val loginState: StateFlow<MemberInfo?> = _loginState.asStateFlow()

    // todo 請整理程式碼，相似的東西，例如 MutableStateFlow 請統一放在同一區
    val emailRegex = Patterns.EMAIL_ADDRESS
    val isValidEmail: Boolean
        get() = emailRegex.matcher(email.value).matches()

    private val _snackbarTrigger = MutableStateFlow(0)
    val snackbarTrigger: StateFlow<Int> = _snackbarTrigger.asStateFlow()

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage.asStateFlow()

    init {
        val savedToken = preferences.getString("member_token", null)
        val savedEmail = preferences.getString("email", null)
        val savedPassword = preferences.getString("password", null)

        if (
            savedToken != null && savedEmail != null && savedPassword != null
        ) {
            email.value = savedEmail
            mpassword.value = savedPassword
            Log.d("LoginViewModelValue", "email: ${email.value}, password: ${mpassword.value}")
            try {
                viewModelScope.launch {
                    login()
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Auto login failed", e)
            }
        }
    }

    fun login() {
        viewModelScope.launch {
            if (email.value.isBlank() || mpassword.value.isBlank()) {
                _snackbarMessage.value = "empty_fields"
                _snackbarTrigger.value += 1
            }

            val memberInfo = repository.login(email.value, mpassword.value)
            if (memberInfo != null) {
                _loginState.update { memberInfo }
                onLoginClicked()
                // user 資料儲存
                saveMemberInfoToPreferences(memberInfo)
                _snackbarMessage.value = "Login success"
                _snackbarTrigger.value += 1
            } else {
                _snackbarMessage.value = "使用者帳號或密碼錯誤"
                _snackbarTrigger.value += 1
            }
        }
    }

    private fun saveMemberInfoToPreferences(memberInfo: MemberInfo) {
        with(preferences.edit()) {
            putString("email", memberInfo.email)
            putString("password", memberInfo.mpassword)
            putString("member_name", memberInfo.member_name)
            putInt("member_no", memberInfo.member_no)
            putString("member_nick_name", memberInfo.member_nick_name)
            putString("phone", memberInfo.phone)
            putString("introduction", memberInfo.introduction)
            putBoolean("member_status", memberInfo.member_status)
            putString("member_token", memberInfo.member_token)
            apply()
        }
    }




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

    // 登出方法
    fun logout() {
        with(preferences.edit()) {
            clear()  // 清除所有儲存的資料
            apply()
        }
        _loginState.value = null
        email.value = ""
        mpassword.value = ""
    }
}