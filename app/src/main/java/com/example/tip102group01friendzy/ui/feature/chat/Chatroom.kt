package com.example.tip102group01friendzy.ui.feature.chat

import com.example.tip102group01friendzy.R

class Chatroom (
    var roomNo: String ="",
    var UserTwoName : String="",
    var image: Int= R.drawable.puzzle,
    var lastMessageTime: String =""
){
    override fun equals(other: Any?): Boolean {
        return this.roomNo == (other as Chatroom).roomNo
    }

    override fun hashCode(): Int {
        return roomNo.hashCode()
    }
}