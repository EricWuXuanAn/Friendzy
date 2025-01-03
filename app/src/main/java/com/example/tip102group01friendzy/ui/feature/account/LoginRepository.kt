package com.example.tip102group01friendzy.ui.feature.account

import android.util.Log
import com.example.tip102group01friendzy.Member
import com.example.tip102group01friendzy.RetrofitInstance

class LoginRepository {
    private val tag = "tag_RequestVM"

    suspend fun login(email: String, mpassword: String):LoginResponse {
        return try {
            Log.d(tag, "email: $email, mpassword: $mpassword")
            val response = RetrofitInstance.api.login(Member(email, mpassword))
            Log.d(tag, "response: $response")
            if (response.isSuccessful) {
                val result = response.body()
                if (result != null) {
                    LoginResponse.Success(result)
                } else {
                    LoginResponse.Error("回應資料為空")
                }
            } else {
                LoginResponse.Error("登入失敗: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e(tag, "error: ${e.message}")
            LoginResponse.Error("網路錯誤: ${e.message}")
        }
    }
}