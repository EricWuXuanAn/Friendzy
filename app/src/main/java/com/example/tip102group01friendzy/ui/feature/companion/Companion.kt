package com.example.tip102group01friendzy.ui.feature.companion


data class Companion(
    /*
//    var companionNO :String = "",//陪伴者(會員編號)
//    var companionCity :String = "",//陪伴者所在城市

    var memberNo :String = "a0001",//刊登者(會員編號)
    var memberName :String = "暱稱ab01234",//刊登者名稱
//    var memberCity :String = "",//刊登者城市
    var memberImg :Int = 0, //刊登者頭像
//    var serviceId :String = "",//服務單號(流水號)
    var serviceTitle: String ="",//服務項目
//    var servicessss: String = "",//服務細項
//    var serviceImg :Int = R.drawable.friendzy, //服務項目照片
//    var //開始時間
//    var //結束時間
//    var budget: String = "" //金額
     */
    var userID: Int = 1,
    var userName: String = "",
    var userStatus:Boolean =true,
    ) {
    override fun equals(other: Any?): Boolean {
//        return this.memberNo == (other as Companion).memberNo
        return this.userID == (other as Companion).userID
    }

    override fun hashCode(): Int {
//        return memberNo.hashCode()
        return userID.hashCode()
    }
}