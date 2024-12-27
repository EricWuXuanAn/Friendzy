package com.example.tip102group01friendzy.ui.feature.account

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ForgetPasswordViewModel : ViewModel(){
    var email = mutableStateOf("")

    val emailRegex = Patterns.EMAIL_ADDRESS
    val isValidEmail: Boolean
        get() = emailRegex.matcher(email.value).matches()

    private val _snackbarTrigger = MutableStateFlow(0)
    val snackbarTrigger: StateFlow<Int> = _snackbarTrigger.asStateFlow()

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage.asStateFlow()

    fun onForgetPasswordClecked(){
        if (email.value.isBlank()){
            _snackbarMessage.value = "Email cannot be empty."
            _snackbarTrigger.value +=1
        }else if (!isValidEmail){
            _snackbarMessage.value = "Email Formatting Error."
            _snackbarTrigger.value +=1
        }else{
            //TODO: 確認曾經註冊過且信箱正確就寄信
            _snackbarMessage.value = null
        }
    }
}