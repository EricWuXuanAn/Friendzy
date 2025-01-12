package com.example.tip102group01friendzy.ui.feature.companion

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tip102group01friendzy.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CompanionOrderVM : ViewModel() {
    //訂單列表
    private val _orderListState = MutableStateFlow(emptyList<CompanionOrder>())
    val orderListState = _orderListState.asStateFlow()
    //訂單明細
    private val _orderSelectState = MutableStateFlow<CompanionOrder?>(null)
    val orderSelectState = _orderSelectState.asStateFlow()

    //取得所有訂單
    fun getOrderList(memberNo :Int){
        viewModelScope.launch {
            _orderListState.value = fetchOrderList(memberNo)
        }
    }

    //取得訂單明細
    fun getSelectOrder(memberNo: Int, servicePoster: Int, orderId: Int) {
        viewModelScope.launch {
//        Log.d("_tag-setSelectOrder","memberNo：$memberNo , orderPoster： ${servicePoster} , orderId：${orderId}")
            val value = fetchOrderId(memberNo,servicePoster,orderId)
//        Log.e("_tagFetchOrderVM", Gson().toJson(value))
//            Log.e("_tagValye", "$value")
            _orderSelectState.update { value }
//            _selectDate.update { value }
//        Log.d("_tagDetailSelect","$_orderDetailsSelectState")
        }
    }

    //重置訂單明細
    fun reSelectOrder(){
        _orderSelectState.update { CompanionOrder() }
//        _selectDate.update { CompanionOrder() }
    }

    //更改訂單狀態
    fun setOrderStatus(orderId: Int, status: Int, memberNo: Int){
        viewModelScope.launch {
            val value = fetchOrderStatus(orderId, status)
            Log.d("_tagUpdate status","orderId:$orderId ,status:$status ")
            Log.d("_tagUpdate status","value:$value")
            _orderSelectState.update { value }
//            _orderListState.value = fetchOrderList(memberNo = memberNo)
        }
    }
    //更改評價資料
    fun setRate(memberNo: Int,poster: Int, posterStatus: Int, rate: Int, rateContent: String, orderId: Int){
        viewModelScope.launch {
            fetchUpdateRate(
                memberNo =  memberNo,
                poster =  poster,
                rate =  rate,
                posterStatus =  posterStatus,
                rateContent =  rateContent,
                orderId = orderId)
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
    private suspend fun fetchOrderId(memberNo: Int,servicePoster: Int,orderId: Int): CompanionOrder {
        try {
            val order = RetrofitInstance.api.comOrderDetails(memberNo,servicePoster,orderId)
//            Log.d("_tagFetchOrderVM", "$order")
            return order
        } catch (e: Exception) {
            Log.d("_tagFetchOrderVM", "error:${e.message}")
            return CompanionOrder()
        }
    }

    //更改訂單用
//    private val _selectDate = MutableStateFlow<CompanionOrder?>(null)
    //修改訂單編號用的資料
    private fun selectDateUpdate( status: Int):CompanionOrder {
        val date = _orderSelectState.asStateFlow().value!!
//        date.orderId = orderId
        date.orderStatus = status
        Log.d("_tagDate","date:$date")
        return date
    }
    //修改訂單編號
    private suspend fun fetchOrderStatus(orderId: Int, status: Int):CompanionOrder{
        try {
//            Log.d("_tagUpdate statusAPI","orderId:$orderId ,status:$status ")
            val order = selectDateUpdate(status)
//            Log.d("_tagUpdate","date:$order")
            val status = RetrofitInstance.api.comOrderUpdate(
                comOrder =
//                CompanionOrder(orderId = orderId,orderStatus = status)
                order
            )
            Log.d("_tagUpdate","連接API")
            return status
//            return order
        }catch ( e:Exception ){
            return CompanionOrder()
        }
    }
    //評價
    private suspend fun fetchUpdateRate(memberNo: Int ,poster: Int, posterStatus: Int,rate: Int,rateContent: String,orderId: Int): CompanionOrder{
        try {
            val rate = RetrofitInstance.api.comOrderUpdateRate(
                CompanionOrder(memberNo = memberNo,
                    orderPoster = poster,
                    posterStatus = posterStatus,
                    cusRate = rate,
                    comRate = rate,
                    cusRateContent = rateContent,
                    comRateContent = rateContent,
                    orderId = orderId
                )
            )
            return rate
        }catch (e:Exception){
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
    var orderPrice: Double? = null,//金額

    var memberNo: Int? = null,
    var posterStatus: Int? = null,

) {
}
