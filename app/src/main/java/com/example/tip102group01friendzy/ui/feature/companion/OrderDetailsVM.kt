package com.example.tip102group01friendzy.ui.feature.companion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ComOrderDtlVM :ViewModel(){
    private val _orderDtlState  = MutableStateFlow(emptyList<ComOrderDtl>())
    val orderDtlState:StateFlow<List<ComOrderDtl>> = _orderDtlState.asStateFlow()

    init {
        viewModelScope.launch {
            _orderDtlSelectState.value = fetchOrderDtl()
//            _orderDtlState.value = fetchOrderDtl()
        }
    }

    private val _orderDtlSelectState = MutableStateFlow(ComOrderDtl())
    val OrderDtlSelectState:StateFlow<ComOrderDtl> = _orderDtlSelectState.asStateFlow()
    fun setSelectorderDtl(orderDtl: ComOrderDtl){
        _orderDtlSelectState.value = orderDtl
    }

//    private suspend fun fetchOrderDtl():ComOrderDtl{
//    private fun fetchOrderDtl():List<ComOrderDtl>{
//        return listOf(
//            ComOrderDtl(orderId = 1, memberName = "毛安", memberImg = "", title = "一起打羽球", orderPoster = "AAAA", orderPerson = "毛安", startTime = "2024/01/01 14:00", endTime = "2024/01/01 26:00", orderStatus = 2, cusRateContent = "", comRateContent = "", cusRate = 0, comRate = 0),
//        )
//    }
    private fun fetchOrderDtl(): ComOrderDtl{
        return ComOrderDtl(orderId = 1, memberName = "毛安", memberImg = "", title = "一起打羽球", orderPoster = "AAAA", orderPerson = "毛安", startTime = "2024/01/01 14:00", endTime = "2024/01/01 26:00", orderStatus = 2, cusRateContent = "", comRateContent = "", cusRate = 0, comRate = 0)
    }
}


//對方頭像、對方名字、訂單編號、標題、刊登人、訂購人、開始時間、結束時間、訂單狀態、雙方評分、雙方評論
data class ComOrderDtl(
    var orderId:Int = 0,//訂單編號
    var memberName:String = "名字AAA",//對方名字
    var memberImg:String = "",//對方頭像
    var title:String = "我是標題",//標題
    var orderPoster: String = "刊登人A",//刊登者
    var orderPerson: String = "訂購人A",//訂購人
    var startTime: String = "yyyy-MM-dd hh:mm",//開始時間
    var endTime: String = "yyyy-MM-dd hh:mm",//結束時間
    var orderStatus: Int = 3,//訂單狀態
    var cusRateContent: String = "評論1111111",//顧客評論
    var comRateContent: String = "評論2222222",//陪伴者評論
    var cusRate: Int = 4,//顧客評論
    var comRate: Int = 3,// 陪伴者評論
){
    override fun equals(other: Any?): Boolean {
        return this.orderId == (other as ComOrderDtl).orderId
    }

    override fun hashCode(): Int {
        return orderId.hashCode()
    }
}