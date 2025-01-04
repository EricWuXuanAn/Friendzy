package com.example.tip102group01friendzy.ui.feature.account

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tip102group01friendzy.RequestVM
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel (): ViewModel() {
    var email = mutableStateOf("")
    var mpassword = mutableStateOf("")
    var confirmPassword = mutableStateOf("")
    var member_name = mutableStateOf("")

    val emailRegex = Patterns.EMAIL_ADDRESS
    val isValidEmail: Boolean
        get() = emailRegex.matcher(email.value).matches()

    private val _errorRequest = MutableStateFlow<List<String>>(emptyList())
    val errorRequest: StateFlow<List<String>> = _errorRequest.asStateFlow()

    private val _naviRequest = MutableStateFlow<Boolean?>(null)
    val naviRequest = _naviRequest.asStateFlow()

    private fun validateInputs(): Boolean{
        var isValid = true
        _errorRequest.update { emptyList() }

        if (email.value.isBlank() || mpassword.value.isBlank() ||
            confirmPassword.value.isBlank() || member_name.value.isBlank()) {
            _errorRequest.update { it + "Field cannot be empty." }
            isValid = false
        }
        if (!isValidEmail) {
            _errorRequest.update { it + "Email Formatting Error." }
            isValid = false
        }
        if (mpassword.value.isNotBlank() && mpassword.value.count() < 8) {
            _errorRequest.update { it + "Password(at least 8 characters)" }
            isValid = false
        }
        if (mpassword.value != confirmPassword.value) {
            _errorRequest.update { it + "Password do not match." }
            isValid = false
        }

        return isValid
    }

//    fun onRegisterClicked() {
//
//        if (email.value.isBlank() || mpassword.value.isBlank() || confirmPassword.value.isBlank() || member_name.value.isBlank()) {
//            _errorRequest.update { currentList ->
//                currentList + "Field cannot be empty."
//            }
//        }
//        if (!isValidEmail) {
//            _errorRequest.update { currentList ->
//                currentList + "Email Formatting Error."
//            }
//        }
//        if (mpassword.value.isNotBlank() && mpassword.value.count() < 8) {
//            _errorRequest.update { currentList ->
//                currentList + "Password(at least 8 characters)"
//            }
//        }
//        if (mpassword.value != confirmPassword.value) {
//            _errorRequest.update { currentList ->
//                currentList + "Password do not match."
//            }
//        }
//        if (!_errorRequest.value.any()){
//            _naviRequest.update { true }
//        }
//    }

    fun consumeErrorRequest() {
        _errorRequest.update { emptyList() }
    }

    fun consumeNaviRequest() {
        _naviRequest.update { null }
    }

    fun onRegisterButtonClicked(requestVM: RequestVM){
        viewModelScope.launch {
            if (validateInputs()){
                val response = requestVM.createMember(
                    email.value,
                    mpassword.value,
                    member_name.value
                )
                if(response == null){
                    _errorRequest.update { currentList -> currentList +"Email already registered." }
                }else{_naviRequest.update { true }}
            }
        }
    }
}