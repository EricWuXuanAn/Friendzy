package com.example.tip102group01friendzy.ui.feature.companion

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tip102group01friendzy.RetrofitInstance
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.log

class CompanionOrderVM : ViewModel() {
    private val _orderListState = MutableStateFlow(emptyList<CompanionOrder>())
    val orderListState = _orderListState.asStateFlow()

    private val _orderDetailsSelectState = MutableStateFlow<CompanionOrder?>(null)
    val orderDetailsSelectState = _orderDetailsSelectState.asStateFlow()

    fun getOrderList(memberNo :Int){
        viewModelScope.launch {
            _orderListState.value = fetchOrderList(memberNo)
        }
    }

    //取得訂單明細
    fun setSelectOrder(memberNo: Int,servicePerson: Int,orderId: Int) {
        viewModelScope.launch {
            Log.d("_tag-setSelectOrder","memberNo：$memberNo , orderPoster： ${servicePerson} , orderId：${orderId}")
            val value = fetchOrderId(memberNo,servicePerson,orderId)
//        Log.e("_tagFetchOrderVM", Gson().toJson(value))
            Log.e("_tagValye", "$value")
            _orderDetailsSelectState.update { value }
//        Log.d("_tagDetailSelect","$_orderDetailsSelectState")
        }
    }

    //更改訂單狀態
    fun setOrderStatus(orderId: Int,status: Int,memberNo: Int){
        viewModelScope.launch {
            val value = fetchOrderStatus(orderId, status)
            _orderDetailsSelectState.update { value }
            _orderListState.update { fetchOrderList(memberNo) } //更新後取得訂單列表
            Log.d("_orderList", "${fetchOrderList(memberNo)}")
        }
    }

    //取得所有訂單
    private suspend fun fetchOrderList(memberNo: Int): List<CompanionOrder> {
        try {
            val orderList = RetrofitInstance.api.comShowAllOrder(memberNo)
            return orderList
        } catch (e: Exception) {
            return emptyList()
        }
    }
    //取得指定ID的訂單
    private suspend fun fetchOrderId(memberNo: Int,servicePerson: Int,orderId: Int): CompanionOrder {
        try {
            val order = RetrofitInstance.api.comOrderDetails(memberNo,servicePerson,orderId)
            Log.d("_tagFetchOrderVM", "$order")
            return order
        } catch (e: Exception) {
            Log.d("_tagFetchOrderVM", "error:${e.message}")
            return CompanionOrder()
        }
    }
    //該改訂單編號
    private suspend fun fetchOrderStatus(orderId: Int, status: Int):CompanionOrder{
        try {
            val status = RetrofitInstance.api.comOrderUpdate(
                CompanionOrder(orderId = orderId,orderStatus = status)
            )
            return status
        }catch ( e:Exception ){
            return CompanionOrder()
        }
    }

}

data class CompanionOrder(

    var orderId: Int? = null,//訂單編號
    var serviceId: Int? = null,//服務編號

    var theirId: Int? = null,//對方Id
    var theirName: String? = null,//對方名字
    var orderPersonName: String? = null,//訂購人名字
    var orderPerson: Int? = null,//訂購人編號
    var orderPosterName: String? = null,//刊登人名字
    var orderPoster: Int? = null,//刊登人編號

    var startTime: Long? = null,//開始時間
    var endTime: Long? = null,//結束時間

    var orderStatus: Int? = null,//訂單狀態
    var serviceStatus: Int? = null,//服務狀態

    var service: String? = null,//標題
    var cusRateContent: String? = null,//顧客評論
    var cusRate: Int? = null,//顧客評分
    var comRateContent: String? = null,//陪伴者評論
    var comRate: Int? = null,//陪伴者頻分
    var area: String? = null,//地區
    var ordePrice: Double? = null,//金額

) {
}
