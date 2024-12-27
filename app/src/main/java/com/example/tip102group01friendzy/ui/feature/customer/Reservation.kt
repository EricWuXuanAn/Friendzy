package com.example.tip102group01friendzy.ui.feature.customer

import com.example.tip102group01friendzy.R

class Reservation (
    var memberID: String = "",
    var memberName: String = "",
    var memberImg: Int = R.drawable.ic_launcher_foreground,
    var title: String = "",
    var specialty: String = "",
    var loaction: String = "",
    var price: Double
//    var startTime: Long,
//    var endTime: Long
){
    override fun equals(other: Any?): Boolean {
        return this.memberID == (other as Reservation).memberID
    }

    override fun hashCode(): Int {
        return this.memberID.hashCode()
    }
}