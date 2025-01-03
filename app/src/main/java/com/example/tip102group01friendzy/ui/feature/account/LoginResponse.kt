package com.example.tip102group01friendzy.ui.feature.account

import com.example.tip102group01friendzy.Result


sealed class LoginResponse {
    data class Success(val result: Result) : LoginResponse()
    data class Error(val message: String) : LoginResponse()
}