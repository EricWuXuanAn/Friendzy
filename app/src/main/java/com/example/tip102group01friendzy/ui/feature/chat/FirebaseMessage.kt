package com.example.tip102group01friendzy.ui.feature.chat

data class FirebaseMessage(
    val messageId: String = "",
    val content: String = "",
    val senderId: Int = 0,
    val timestamp: Long = 0,
    val roomNo: Int = 0
)