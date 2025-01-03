package com.example.tip102group01friendzy

import android.util.Log
import androidx.lifecycle.ViewModel


class RequestVM: ViewModel(){
    private val tag = "tag_RequestVM"

    suspend fun login(email: String, mpassword: String):Boolean{
        try {
            Log.d(tag, "email: $email, mpassword: $mpassword")
            val response = RetrofitInstance.api.login(email, mpassword)
            Log.d(tag, "response: $response")
            return response.isSuccessful

        }catch (e:Exception){
            Log.e(tag, "error: ${e.message}")
            return false
        }
    }

    suspend fun CreateMember(email: String, mpassword: String, member_name: String):CreatMemberResponce?{
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