package com.example.tip102group01friendzy

import androidx.lifecycle.ViewModel


class RequestVM: ViewModel(){

    suspend fun CreateMember(email: String, mpassword: String, member_name: String):CreatMemberResponce?{
        try {
            val responce = RetrofitInstance.api.createMember(
                CreateMemberRequest(email, mpassword, member_name)
            )
            return responce
        }catch (e: Exception){
            return null
        }
    }
}