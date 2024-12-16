package com.example.tip102group01friendzy

class Customer(
    var memberName: String = "",
    var phoneNum: String = "",
    var membeerNum:String = "",
    var memberImg:Int = R.drawable.ic_launcher_foreground
) {
    override fun equals(other: Any?): Boolean {
        return this.membeerNum == (other as Customer).membeerNum
    }

    override fun hashCode(): Int {
        return membeerNum.hashCode()
    }
}