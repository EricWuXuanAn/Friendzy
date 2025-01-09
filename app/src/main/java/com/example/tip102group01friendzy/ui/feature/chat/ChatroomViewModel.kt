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

    private val _resultState = MutableStateFlow<Chatroom?>(null)
    val resultState = _resultState.asStateFlow()

    init {
        viewModelScope.launch {
            //本地資料庫
            val localChatrooms = getChatroomInfo()
            _chatroomState.value = localChatrooms
            //同步到Firebase
            localChatrooms.forEach { chatroom ->
                syncChatroomToFirebase(chatroom)
            }
            //監聽Firebase變化
            observeFirebaseUpdates()
        }
    }

    private fun syncChatroomToFirebase(chatroom: Chatroom) {
        database.reference
            .child("chatRooms")
            .child(chatroom.room_no.toString())
            .setValue(
                mapOf(
                    "room_no" to chatroom.room_no,
                    "room_user_one" to chatroom.room_user_one,
                    "room_user_two" to chatroom.room_user_two,
                    "OtherUserName" to chatroom.OtherUserName
                )
            )
    }

    private fun observeFirebaseUpdates() {
        database.reference.child("chatRooms")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    viewModelScope.launch {
                        val updateChatrooms = getChatroomInfo()
                        _chatroomState.value = updateChatrooms
                    }
//                    val chatrooms = mutableListOf<Chatroom>()
//                    snapshot.children.forEach { roomSnapshot ->
//                        val room = roomSnapshot.getValue(Chatroom::class.java)
//                        room?.let { chatrooms.add(it) }
//                    }
//                    _chatroomState.value = chatrooms
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Error loading chatrooms: ${error.message}")
                }
            }
            )
    }

    //找聊天室是否已存在
    suspend fun checkChatroomExists(currentUserId: Int, otherUserId: Int): Int?{
        return try {
            val chatrooms = getChatroomInfo()
            chatrooms.find { room ->
                (room.room_user_one == currentUserId && room.room_user_two == otherUserId) ||
                        (room.room_user_one == otherUserId && room.room_user_two == currentUserId)
            }?.room_no
        }catch (e: Exception){
            null
        }
    }

    suspend fun createAndGetChatroom(otherUserId: Int): Int? {
        return try {
            val response = RetrofitInstance.api.createChatroom(otherUserId)
            if (response.isSuccessful) {
                val newChatroom = response.body()
                newChatroom?.let {
                    syncChatroomToFirebase(it)
                    return it.room_no
                }
            }
            null
        }catch (e: Exception){
            null
        }
    }

    //拿聊天室資料
    suspend fun getChatroomInfo(): List<Chatroom> {
        try {
            val allChatrooms = RetrofitInstance.api.showAllChatrooms()
            val uniqueChatRooms = allChatrooms.distinctBy { chatroom ->
                val userIds = listOf(chatroom.room_user_one, chatroom.room_user_two).sorted()
                "${userIds[0]}_${userIds[1]}"
            }
            Log.d("ChatroomViewModel", "allChatrooms: $allChatrooms, uniqueChatRooms: $uniqueChatRooms")
            return uniqueChatRooms
        } catch (e: Exception) {
            Log.e("ChatroomViewModel", "Error fetching chatrooms", e)
            return emptyList()
        }
    }
}