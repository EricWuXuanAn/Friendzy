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


class SettingViewModel : ViewModel() {

    // 使用 MutableStateFlow 存儲會員信息，提供雙向數據綁定的能力
    private val _memberInfo = MutableStateFlow(MemberModel.MemberInfo(null, null, null, null, null,null,null,null,null,null))
    val memberInfo: StateFlow<MemberModel.MemberInfo> = _memberInfo // 暴露為只讀的 StateFlow 給外部觀察

    // 從後端獲取會員信息並更新至 _memberInfo
    fun fetchMemberInfo(context: Context) {
        viewModelScope.launch { // 啟動協程
            val email = getEmailFromPreferences(context) // 從 SharedPreferences 獲取 email
            if (email != null) {
                val response = getMemberAPI(email) // 使用 email 調用 API 獲取數據
                Log.d("getMemberAPI", "response: $response") // 日誌打印響應數據
                if (response != null) { // 確保返回的數據不為空
                    _memberInfo.value = response // 更新 StateFlow 的值
                }
            } else {
                Log.e("fetchMemberInfo", "Email 未找到")
            }
        }
    }

    // 更新會員密碼
    fun updatePassword(context: Context, newPassword: String) {
        viewModelScope.launch { // 啟動協程
            val email = getEmailFromPreferences(context) // 從 SharedPreferences 獲取 email
            if (email != null) {
                updatePasswordAPI(email, newPassword) // 傳遞所有參數
            } else {
                Log.e("updatePassword", "Email, Nickname 或 Phone 未找到")
            }
        }
    }

    // 調用後端 API 更新會員密碼
    private suspend fun updatePasswordAPI(email: String, password: String, ) {
        val request = MemberModel.editpassword(
            email = email,
            newPassword = password // 更新密碼
        )
        val response = RetrofitInstance.api.editpassword(request) // 調用 API
        Log.d("updatePasswordAPI", "API 響應: $response")
    }



    // 更新會員暱稱
    fun updateNickname(context: Context, newNickname: String) {
        viewModelScope.launch { // 啟動協程
            val email = getEmailFromPreferences(context) // 從 SharedPreferences 獲取 email
            if (email != null) {
                updateNicknameAPI(email, newNickname) // 傳遞 email 和新暱稱
            } else {
                Log.e("updateNickname", "Email 未找到")
            }
        }
    }

    // 調用後端 API 更新會員暱稱
    private suspend fun updateNicknameAPI(email: String, nickname: String) {
        val request = MemberModel.editnickname(email = email, nickname = nickname)
        val response = RetrofitInstance.api.editnickname(request) // 調用 API
        Log.d("updateNicknameAPI", "API 響應: $response")
    }

    // 更新會員電話號碼
    fun updatePhoneNumber(context: Context, newPhone: String) {
        viewModelScope.launch { // 啟動協程
            val email = getEmailFromPreferences(context) // 從 SharedPreferences 獲取 email
            if (email != null) {
                updatePhoneNumberAPI(email, newPhone) // 傳遞 email 和新電話號碼
            } else {
                Log.e("updatePhoneNumber", "Email 未找到")
            }
        }
    }

    // 調用後端 API 更新會員電話號碼
    private suspend fun updatePhoneNumberAPI(email: String, phone: String) {
        val request = MemberModel.editphone(email = email, phone = phone)
        val response = RetrofitInstance.api.editphone(request) // 調用 API
        Log.d("updatePhoneNumberAPI", "API 響應: $response")
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
