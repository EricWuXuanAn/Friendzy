package com.example.tip102group01friendzy.ui.feature.chat

import androidx.lifecycle.ViewModel
import com.example.tip102group01friendzy.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChatroomViewModel : ViewModel() {
    //私有不讓外部直接更改，用沒底線的讓外部讀取
    private val _chatroomState = MutableStateFlow(emptyList<Chatroom>())

    val chatroomState: StateFlow<List<Chatroom>> = _chatroomState.asStateFlow()

    init {
        _chatroomState.update { getChatroomInfo() }
    }

    //新增聊天室
    fun addChatroom(item: Chatroom) {
        _chatroomState.update {
            val chatrooms = it.toMutableList()
            chatrooms.add(item)
            chatrooms
        }
    }


    //聊天室假資料
    private fun getChatroomInfo(): List<Chatroom> {
        return listOf(
            Chatroom("0001", "James Smith", lastMessageTime = "2024-12-16 10:30"),
            Chatroom("0002", "Emily Johnson", R.drawable.chatroom2, "2024-12-15 02:20" ),
            Chatroom("0003", "Michael Brown", R.drawable.chatroom3, "2024-12-14 08:15" ),
            Chatroom("0004", "Mia Robinson", lastMessageTime = "2024-12-16 10:30"),
            Chatroom("0005", "Isabella Thomas", lastMessageTime = "2024-12-16 10:30"),
            Chatroom("0006", "Lucas Martinez", lastMessageTime = "2024-12-16 10:30"),
            Chatroom("0007", "Sophia Anderson", lastMessageTime = "2024-12-16 10:30"),
            Chatroom("0008", "Noah Taylor", lastMessageTime = "2024-12-16 10:30"),
            Chatroom("0009", "Ava Wilson", lastMessageTime = "2024-12-16 10:30"),
            Chatroom("0010", "Emma Brown", lastMessageTime = "2024-12-16 10:30"),
            Chatroom("0011", "Emma Brown", lastMessageTime = "2024-12-16 10:30"),
            Chatroom("0012", "Emma Brown", lastMessageTime = "2024-12-16 10:30"),
            Chatroom("0013", "Emma Brown", lastMessageTime = "2024-12-16 10:30"),
            Chatroom("0014", "Emma Brown", lastMessageTime = "2024-12-16 10:30"),
        )
    }
}