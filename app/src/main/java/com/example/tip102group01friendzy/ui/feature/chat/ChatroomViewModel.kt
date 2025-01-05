package com.example.tip102group01friendzy.ui.feature.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatroomViewModel : ViewModel() {
    //私有不讓外部直接更改，用沒底線的讓外部讀取
    private val _chatroomState = MutableStateFlow(emptyList<Chatroom>())
    val chatroomState: StateFlow<List<Chatroom>> = _chatroomState.asStateFlow()

    init {
//        _chatroomState.update { getChatroomInfo() } //假資料時的初始
        viewModelScope.launch {
            _chatroomState.value = getChatroomInfo()
        }
    }

    //新增聊天室
    fun addChatroom(item: Chatroom) {
        _chatroomState.update {
            val chatrooms = it.toMutableList()
            chatrooms.add(item)
            chatrooms
        }
    }


    //拿聊天室資料
    suspend fun getChatroomInfo(): List<Chatroom> {
        try {
            val  list = RetrofitInstance.api.showAllChatrooms()
            Log.d("ChatroomViewModel", "Fetched chatrooms: $list")
            return list
        }catch (e:Exception){
            Log.e("ChatroomViewModel", "Error fetching chatrooms", e)
            return  emptyList()
        }
    }
}