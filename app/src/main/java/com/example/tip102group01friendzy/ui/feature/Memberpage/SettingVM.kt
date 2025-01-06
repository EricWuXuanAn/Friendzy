package com.example.tip102group01friendzy.ui.feature.Memberpage


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tip102group01friendzy.MemberModel
import com.example.tip102group01friendzy.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class MemberInfo(
    val email: String = "",    // 會員的電子郵件
    val nickname: String = "", // 會員的暱稱
    val phone: String = ""     // 會員的手機號碼
)

class SettingViewModel : ViewModel() {

    // 使用 MutableStateFlow 存儲會員信息，提供雙向數據綁定的能力
    private val _memberInfo = MutableStateFlow(MemberInfo())
    val memberInfo: StateFlow<MemberInfo> = _memberInfo // 暴露為只讀的 StateFlow 給外部觀察

    // 從後端獲取會員信息並更新至 _memberInfo
    fun fetchMemberInfo() {
        viewModelScope.launch { // 啟動協程
            val response = getMemberAPI() // 調用 API 獲取數據
            if (response != null) { // 確保返回的數據不為空
                _memberInfo.value = response // 更新 StateFlow 的值
            }
        }
    }

    // 更新會員密碼
    fun updatePassword(newPassword: String) {
        viewModelScope.launch { // 啟動協程
            updatePasswordAPI(newPassword) // 調用 API 傳遞新密碼
        }
    }

    // 更新會員昵稱
    fun updateNickname(newNickname: String) {
        viewModelScope.launch { // 啟動協程
            updateNicknameAPI(newNickname) // 調用 API 傳遞新昵稱
        }
    }

    // 更新會員電話號碼
    fun updatePhoneNumber(newPhone: String) {
        viewModelScope.launch { // 啟動協程
            updatePhoneNumberAPI(newPhone) // 調用 API 傳遞新電話號碼
        }
    }

    // 模擬後端 API 調用，獲取會員信息
    private suspend fun getMemberAPI(): MemberInfo? {
        // 調用 Retrofit API 以獲取會員信息
        val response = RetrofitInstance.api.findMemberInfo()
        if (response!=null){
            return MemberInfo( // 將後端返回的數據映射到 MemberInfo 對象
                email = response?.email?:"", // 獲取會員的電子郵件
                nickname = response?.nickname?:"", // 獲取會員的昵稱
                phone = response?.phone?:"" // 獲取會員的電話號碼
            )
        }else{
            return null
        }
        Log.d("response", response.toString()) // 日誌打印響應數據

    }

    // 調用後端 API 更新會員密碼
    private suspend fun updatePasswordAPI(password: String) {
        // 創建 API 所需的請求對象，僅傳遞新密碼
        val request = MemberModel.edit(nickname = "", phone = "", newPassword = password)
        val response = RetrofitInstance.api.edit(request) // 調用 API
    }

    // 調用後端 API 更新會員昵稱
    private suspend fun updateNicknameAPI(nickname: String) {
        // 創建 API 所需的請求對象，僅傳遞新昵稱
        val request = MemberModel.edit(nickname = nickname, phone = "", newPassword = "")
        val response = RetrofitInstance.api.edit(request) // 調用 API
    }

    // 調用後端 API 更新會員電話號碼
    private suspend fun updatePhoneNumberAPI(phone: String) {
        // 創建 API 所需的請求對象，僅傳遞新電話號碼
        val request = MemberModel.edit(nickname = "", phone = phone, newPassword = "")
        val response = RetrofitInstance.api.edit(request) // 調用 API
    }
}
