package com.example.tip102group01friendzy

data class CreateMemberRequest(
    val email: String,
    val mpassword: String,
    val member_name: String
)

data class CreatMemberResponse(
    val statu: Boolean,
    val message: String
)

data class Member(
    val email: String,
    val mpassword: String
)

