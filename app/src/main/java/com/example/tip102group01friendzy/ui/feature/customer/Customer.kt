package com.example.tip102group01friendzy.ui.feature.customer

import com.example.tip102group01friendzy.R

data class Customer(
    var memberName: String = "",
    var phoneNum: String = "",
    var membeerNum:String = "",
    var memberImg:Int = R.drawable.ic_launcher_foreground,
    var memberSpecialty: String = ""
) {
//    override fun equals(other: Any?): Boolean {
//        return this.membeerNum == (other as Customer).membeerNum
//    }
//
//    override fun hashCode(): Int {
//        return membeerNum.hashCode()
//    }
}