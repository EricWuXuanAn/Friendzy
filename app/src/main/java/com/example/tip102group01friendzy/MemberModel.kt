package com.example.tip102group01friendzy

import com.google.gson.annotations.SerializedName

class MemberModel {
    data class MemberInfoResponse (
        val statu: Boolean,
         val message: String?,
         val token: String?,
         val data: MemberInfo?
    )

    // 回應模型
    data class MemberInfo(
        @SerializedName("email") val email: String?,                    // 帳號 (信箱)
        @SerializedName("member_name") val name: String?,               // 姓名
        @SerializedName("mpassword") val password: String?,             // 密碼
        @SerializedName("member_nick_name") val nickname: String?,      // 暱稱
        @SerializedName("phone") val phone: String?,                    // 電話
        @SerializedName("member_no") val memberNo: Int?,                // 會員編號
        @SerializedName("introduction") val introduction: String?,     // 簡介
        @SerializedName("service_area") val serviceArea: String?,      // 服務地區
        @SerializedName("companion_score") val companionScore: Int?,   // (陪伴者)平均評分
        @SerializedName("custmer_score") val customerScore: Int?       // (顧客)平均評分
    )

    // 請求模型
    data class editpassword(
        @SerializedName("email") val email:String,
        @SerializedName("mpassword") val newPassword: String
    )
    // 請求模型
    data class editnickname(
        @SerializedName("email") val email:String,
        @SerializedName("member_nick_name") val nickname: String
    )
    // 請求模型
    data class editphone(
        @SerializedName("email") val email:String,
        @SerializedName("phone") val phone: String
    )

    data class editintroduction(
        @SerializedName("email") val email:String,
        @SerializedName("introduction") val introduction: String
    )

}