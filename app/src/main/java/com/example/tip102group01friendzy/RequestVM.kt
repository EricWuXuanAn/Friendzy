package com.example.tip102group01friendzy

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.tip102group01friendzy.ui.feature.account.LoginResponse


class RequestVM: ViewModel(){
    private val tag = "tag_RequestVM"

    suspend fun createMember(email: String, mpassword: String, member_name: String):CreatMemberResponse?{
        try {
            Log.d(tag, "email: $email, mpassword: $mpassword")
            val response = RetrofitInstance.api.createMember(
                CreateMemberRequest(email, mpassword, member_name)
            )
            Log.d(tag, "response: $response")
            return response
        }catch (e: Exception){
            Log.e(tag, "error: ${e.message}")
            return null
        }
    }
}