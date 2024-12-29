package com.example.tip102group01friendzy.ui.feature.fcm

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/** 建立資料共享物件 */
// 需使用單例模式，只要使用「object關鍵字 + 類別名稱」即可
object MyRepository {
    private val _messageState = MutableStateFlow("")
    val messageState: StateFlow<String> = _messageState.asStateFlow()

    fun setMessage(newMessage: String) {
        _messageState.value = newMessage
    }
}