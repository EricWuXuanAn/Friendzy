package com.example.tip102group01friendzy.ui.feature.account

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.tip102group01friendzy.Member
import com.example.tip102group01friendzy.RetrofitInstance
import com.google.gson.Gson


class LoginRepository {
    private val tag = "tag_RequestVM"

    suspend fun login(email: String, mpassword: String): MemberInfo? {
        try {
            Log.d(tag, "email: $email, mpassword: $mpassword")
            val response = RetrofitInstance.api.login(Member(email, mpassword))
            Log.d(tag, "response: $response")
            return response
        } catch (e: Exception) {
            Log.e(tag, "error: ${e.message}")
            return null
        }
    }


}