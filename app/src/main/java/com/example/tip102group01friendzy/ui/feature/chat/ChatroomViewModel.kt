package com.example.tip102group01friendzy.ui.feature.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.RetrofitInstance
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatroomViewModel : ViewModel() {
    private val database = FirebaseDatabase.getInstance()

    //私有不讓外部直接更改，用沒底線的讓外部讀取
    private val _chatroomState = MutableStateFlow(emptyList<Chatroom>())
    val chatroomState: StateFlow<List<Chatroom>> = _chatroomState.asStateFlow()

    init {
        viewModelScope.launch {
            //本地資料庫
            val localChatrooms = getChatroomInfo()
            _chatroomState.value = localChatrooms
            //同步到Firebase
            localChatrooms.forEach { chatroom ->
                syncChatroomToFirebase(chatroom)

            }
            //監聽firebase

        }
    }

    private fun syncChatroomToFirebase(chatroom: Chatroom) {
        database.reference
            .child("chatRooms")
            .child(chatroom.room_no.toString())
            .setValue(
                mapOf(
                    "room_no" to chatroom.room_no,
                    "OtherUserName" to chatroom.OtherUserName
                )
            )
    }

    private fun observeFirebaseUpdates() {
        database.reference.child("chatRooms")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val chatrooms = mutableListOf<Chatroom>()
                    snapshot.children.forEach { roomSnapshot ->
                        val room = roomSnapshot.getValue(Chatroom::class.java)
                        room?.let { chatrooms.add(it) }
                    }
                    _chatroomState.value = chatrooms
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Error loading chatrooms: ${error.message}")
                }
            }
            )
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
            val list = RetrofitInstance.api.showAllChatrooms()
            Log.d("ChatroomViewModel", "Fetched chatrooms: $list")
            return list
        } catch (e: Exception) {
            Log.e("ChatroomViewModel", "Error fetching chatrooms", e)
            return emptyList()
        }
    }
}