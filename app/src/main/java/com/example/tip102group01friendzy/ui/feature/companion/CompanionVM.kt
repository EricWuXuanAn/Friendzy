package com.example.tip102group01friendzy.ui.feature.companion

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tip102group01friendzy.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CompanionVM : ViewModel() {
    private val _applicantListState = MutableStateFlow(emptyList<ComPublish>())
    val applicantListState = _applicantListState.asStateFlow()

    private val _applicantSelectState = MutableStateFlow(ComPublish())
    val applicantSelectState = _applicantSelectState.asStateFlow()
    //取得所有刊登項目
    fun getApplicantList(memberNo: Int) {
        viewModelScope.launch {
            val value = fetchPublishList(memberNo)
            _applicantListState.update { value }
        }
    }
    //取得所選刊登項目詳細資料
    fun getApplicantSelect(serviceId: Int, memberNo: Int) {
        viewModelScope.launch {
            val value = fetchPublishDetail(serviceId,memberNo)
            Log.d("_tagValie","value:${value}")
            _applicantSelectState.update { value }
//            _applicantSelectState.value = value
//            Log.d("_tagValie111","state:${_applicantSelectState.asStateFlow()}")
        }
    }

    private suspend fun fetchPublishList(memberNo: Int): List<ComPublish>{
        try {
            val list = RetrofitInstance.api.showAllPublish(memberNo)
            return list
        }catch (e:Exception){
            return emptyList()
        }
    }

    private suspend fun fetchPublishDetail(serviceId: Int,memberNo: Int):ComPublish{
        try {
            val publish = RetrofitInstance.api.showPublishDetail(serviceId,memberNo)
            Log.d("_tagPublsh","publish:$publish")
            return publish
        }catch (e:Exception){
            Log.e("_tagPublish","publish:error")
            return ComPublish()
        }
    }

}

//清單需要：訂單標號、服務單號、標題、刊登人Id、刊登人名字、開始時間、區域
//詳細需要：內容、結束時間、金額、對方ID(刊登人)、對方名字(刊登人)、刊登人頭像、圖片
data class ComPublish(
//    var memberNo: Int? = null,//使用者編號

    var orderId: Int? = null,//訂單編號
    var serviceId: Int? = null,//服務編號
    var service: String? = null,//標題
    var poster: Int? = null,//刊登人Id
    var posterName: String? = null,//刊登人名字
    var startTime: Long? = null,//開始時間
    var area: String? = null,//區域

    var serviceDetail: String? = null,//內容
    var endTime: Long? = null,//結束時間
    var charge: Double? = null,//金額
    var theirImg: String? = null,//刊登人頭像
    var serviceImg: String? = null,//服務圖片

    var posterStatus: Int? = null,//刊登者身分
//    var accountId: Int? = null,//應徵者Id 確認自己有沒有應徵過
) {
}