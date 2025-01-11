package com.example.tip102group01friendzy.ui.feature.Memberpage

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tip102group01friendzy.MemberModel
import com.example.tip102group01friendzy.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class forothersVM: ViewModel() {
    // 使用 MutableStateFlow 存儲會員信息，提供雙向數據綁定的能力
    private val _memberInfo = MutableStateFlow(MemberModel.MemberInfo(null, null, null, null, null,null,null,null,null,null))
    val memberInfo: StateFlow<MemberModel.MemberInfo> = _memberInfo // 暴露為只讀的 StateFlow 給外部觀察

    // 從後端獲取會員信息並更新至 _memberInfo
    fun fetchMemberInfo(context: Context) {
        viewModelScope.launch { // 啟動協程
            val email = getEmailFromPreferences(context) // 從 SharedPreferences 獲取 email
            if (email != null) {
                val response = getMemberAPI(email) // 使用 email 調用 API 獲取數據
                if (response != null) { // 確保返回的數據不為空
                    _memberInfo.value = response // 更新 StateFlow 的值
                }
            } else {
                Log.e("fetchMemberInfo", "Email 未找到")
            }
        }
    }
    // 模擬後端 API 調用，獲取會員信息
    private suspend fun getMemberAPI(email: String): MemberModel.MemberInfo? {
        val response = RetrofitInstance.api.findMemberInfo()
        Log.d("getMemberAPI", "response: $response") // 日誌打印響應數據
        return response?.data
    }

    // 從 SharedPreferences 獲取 email
    private fun getEmailFromPreferences(context: Context): String? {
        val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        return preferences.getString("email", null)
    }
}