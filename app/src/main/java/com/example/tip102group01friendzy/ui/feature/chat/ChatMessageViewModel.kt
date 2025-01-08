package com.example.tip102group01friendzy.ui.feature.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChatMessageViewModel : ViewModel() {
    private val database = FirebaseDatabase.getInstance()

    private val _messages = MutableStateFlow<List<FirebaseMessage>>(emptyList())
    val messages = _messages.asStateFlow()

    fun loadMessages(roomNo: Int) {
        database.reference.child("messages") //儲存訊息的地方
            .child(roomNo.toString()) //轉成字串加到路徑
            .addValueEventListener(object : ValueEventListener { //監聽器:路徑下資料變動會觸發回調函式
                override fun onDataChange(snapshot: DataSnapshot) {
                    val messageList = mutableListOf<FirebaseMessage>()
                    snapshot.children.forEach { //所有訊息拿出來加到列表中
                        it.getValue(FirebaseMessage::class.java)?.let { message ->
                            messageList.add(message)
                        }
                    }
                    _messages.value = messageList.sortedBy { it.timestamp } //按照時間戳排序
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Error loading messages: ${error.message}")
                }
            })
    }

    fun sendMessage(roomNo: Int, content: String, senderId: Int) {
        val messageRef = database.reference
            .child("messages")
            .child(roomNo.toString())
            .push()

        val message = FirebaseMessage(
            messageId = messageRef.key ?: "",
            content = content,
            senderId = senderId,
            timestamp = System.currentTimeMillis(),
            roomNo = roomNo
        )
        //更新聊天室最後訊息
        messageRef.setValue(message).addOnSuccessListener {
            updateLastMessage(roomNo, message)
        }
    }

    private fun updateLastMessage(roomNo: Int, message: FirebaseMessage) {
        database.reference
            .child("chatRooms")
            .child(roomNo.toString())
            .child("lastMessage")
            .setValue(
                mapOf(
                    "content" to message.content,
                    "spenderId" to message.senderId,
                    "timestamp" to message.timestamp
                )
            )
    }
}