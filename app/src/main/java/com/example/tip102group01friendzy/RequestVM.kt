package com.example.tip102group01friendzy

import android.util.Log
import androidx.lifecycle.ViewModel


class RequestVM: ViewModel(){
    private val tag = "tag_RequestVM"

    suspend fun createMember(email: String, mpassword: String, member_name: String):CreatMemberResponse?{
        try {
            Log.d(tag, "email: $email, mpassword: $mpassword")
            val response = RetrofitInstance.api.createMember(
                CreateMemberRequest(email, mpassword, member_name)
            )
            Log.d(tag, "response: $response")
            if(response?.message =="該帳戶已註冊過，請確認後再執行"){
                return null //註冊過返回null
            }
            return response
        }catch (e: Exception){
            Log.e(tag, "error: ${e.message}")
            return null
        }
    }
}