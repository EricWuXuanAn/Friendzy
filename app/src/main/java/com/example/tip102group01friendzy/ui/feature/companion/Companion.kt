package com.example.tip102group01friendzy.ui.feature.companion

import com.example.tip102group01friendzy.R


data class Companion(
//    var companionNO :String = "",//陪伴者(會員編號)
//    var companionCity :String = "",//陪伴者所在城市
    var memberNo :String = "",//刊登者(會員編號)
    var memberName :String = "",//刊登者名稱
//    var memberCity :String = "",//刊登者城市
    var memberImg :Int = R.drawable.friendzy, //刊登者頭像
//    var serviceId :String = "",//服務單號(流水號)
    var service: String ="",//服務項目
//    var serviceImg :Int = R.drawable.friendzy, //服務項目照片
) {
    override fun equals(other: Any?): Boolean {
        return this.memberNo == (other as Companion).memberNo
    }

    override fun hashCode(): Int {
        return memberNo.hashCode()
    }

}