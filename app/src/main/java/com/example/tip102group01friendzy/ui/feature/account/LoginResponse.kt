package com.example.tip102group01friendzy.ui.feature.account

import com.example.tip102group01friendzy.Result


data class MemberInfo(
    val member_no: Int,
    val email: String,
    val mpassword: String,
    val member_name: String,
    val member_nick_name: String,
    val phone: String,
    val introduction: String,
    val companion_comment: Int,
    val companion_score: Int,
    val custmer_comment: Int,
    val custmer_score: Int,
    val registration_time: String,
    val member_status: Boolean,
    val member_token: String
)

//data class LoginResult(
//    val statu: Boolean,
//    val message: String,
//    val token: String?
//)

//sealed class LoginResponse {
//    data class Success(val memberInfo: MemberInfo, val result: LoginResult) : LoginResponse()
//    data class Error(val message: String) : LoginResponse()
//}