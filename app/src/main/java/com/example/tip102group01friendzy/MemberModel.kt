package com.example.tip102group01friendzy

import com.google.gson.annotations.SerializedName

class MemberModel {
    // 回應模型
    data class MemberInfoResponse(
        @SerializedName("email") val email: String?,
        @SerializedName("member_nick_name") val nickname: String?,
        @SerializedName("phone") val phone: String?
    )

    // 請求模型
    data class edit(
        @SerializedName("member_nick_name") val nickname: String,
        @SerializedName("phone") val phone: String,
        @SerializedName("mpassword") val newPassword: String
    )


}