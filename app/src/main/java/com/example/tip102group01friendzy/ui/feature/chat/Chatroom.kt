package com.example.tip102group01friendzy.ui.feature.chat

data class Chatroom (
    var room_no: Int,
    var OtherUserName : String,
    var room_user_one: Int,
    var room_user_two: Int,
//    var image: Int= R.drawable.puzzle,
//    var lastMessageTime: String =""
){
//    override fun equals(other: Any?): Boolean {
//        return this.roomNo == (other as Chatroom).roomNo
//    }
//
//    override fun hashCode(): Int {
//        return roomNo.hashCode()
//    }
}