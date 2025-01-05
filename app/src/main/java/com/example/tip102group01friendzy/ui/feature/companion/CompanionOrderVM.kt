package com.example.tip102group01friendzy.ui.feature.companion

import android.util.Log
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


    init {
        viewModelScope.launch {
            _orderListState.value = fetchOrderList()
        }
    }

    fun setSelectOrder(order: Int) {
        viewModelScope.launch {
            val value = fetchOrderId(order)
//        Log.e("_tagFetchOrderVM", Gson().toJson(value))
            Log.e("_tagValye", "$value")
            _orderDetailsSelectState.update { value }
//        Log.d("_tagDetailSelect","$_orderDetailsSelectState")
        }
    }

    private suspend fun fetchOrderList(): List<CompanionOrder> {
        try {
            val orderList = RetrofitInstance.api.comShowAllOrder()
            return orderList
        } catch (e: Exception) {
            return emptyList()
        }
    }

    private suspend fun fetchOrderId(id: Int): CompanionOrder {
        try {
            val order = RetrofitInstance.api.comOrderDetails(id)
            Log.d("_tagFetchOrderVM", "$order")
            return order
        } catch (e: Exception) {
            Log.d("_tagFetchOrderVM", "error:${e.message}")
            return CompanionOrder()
        }
    }


    /*
       fun fetchOrderList():List<CompanionOrder>{

           return  listOf(
   //            CompanionOrderList(1, 1, "毛安", 300.0, 0, "一起打羽球"),
   //            CompanionOrderList(2, 3, "阿Miu", 200.0, 1,"教我寫程式"),
   //            CompanionOrderList(3, 2, "阿俊", 150.0, 2, "教我英文"),
   //            CompanionOrderList(4, 5, "Nita", 280.0, 0, "陪我去逛街"),
   //            CompanionOrderList(5, 4, "小宇", 400.0, 0, "陪我打電動"),
   //            CompanionOrderList(6, 1,"Ruby", 250.0, 0, "教我寫程式", true),
   //            CompanionOrderList(7, 2,"綸綸", 100.0, 0, "一起去跑步", true)
               CompanionOrder(1, 1, "毛安","刊登者A", 300.0, 2, "一起打羽球","內容888888888888888"),
               CompanionOrder(2, 3, "阿Miu","刊登者A", 200.0, 1,"教我寫程式","內容655555555555555555"),
               CompanionOrder(3, 2, "阿俊","刊登者B", 150.0, 2, "教我英文","內容22222222222222"),
               CompanionOrder(4, 5, "Nita","刊登者C", 280.0, 0, "陪我去逛街","內容333333333333333",),
               CompanionOrder(5, 4, "小宇", "刊登者B",400.0, 0, "陪我打電動","內容11111111111111111",),
               CompanionOrder(6, 1,"Ruby","刊登者B", 250.0, 0, "教我寫程式","內容12352352242134", reservation = true),
               CompanionOrder(7, 2,"綸綸", "刊登者A",100.0, 0, "一起去跑步","內容asdgwefasdfewfa", reservation = true)
           )
    }
     */
}

data class CompanionOrder(
//    var orderID: Int,
//    var service_IDNO: Int,
//    var order_Person: String = "",
//    var order_Pirce:Double,
//    var order_Status: Int,
//    var order_content:String = "",
//    var reservation:  Boolean = false

    /*
    var orderId: Int = 0,//訂單編號
    var serviceId: Int = 0,//服務單號
    var orderPerson: String = "訂購人A",//訂購人
    var orderPoster: String ="刊登者A",//刊登者
    var orderPirce:Double = 0.0,//金額
    var orderStatus: Int = 0,//訂單狀態
    var orderTitle:String = "我是標題",//標題
    var orderContent:String = "我是內容123456",//內容
    var reservation:  Boolean = false,//暫時用 判斷預約的
    var startTime: Long = 0L,//開始時間
    var endTime: String = "yyyy-MM-dd hh:mm",//結束時間
    */
    var orderId: Int = -1,//訂單編號
    var serviceId: Int = -1,//服務編號
    var theirId: Int = -1,//對方Id
    var theirName: String = "BBBBBBBBB",//對方名字
    var orderPersonName: String = "訂購人A",//訂購人名字
    var orderPerson: Int = -1,//訂購人編號
    var orderPosterName: String = "AAAAAAAA",//刊登人名字
    var orderPoster: Int = -1,//刊登人編號
    var startTime: Long = 0L,//開始時間
    var endTime: Long = 0L,//結束時間
    var orderStatus: Int = -1,//訂單狀態
    var serviceStatus: Int = -1,//服務狀態
    var service: String = "我是標題",//標題
    var cusRateContent: String = "123567465643",//顧客評論
    var cusRate: Int = 0,//顧客評分
    var comRateContent: String = "7564323456",//陪伴者評論
    var comRate: Int = 0,//陪伴者頻分


) {
    override fun equals(other: Any?): Boolean {
        return this.orderId == (other as CompanionOrder).orderId
    }

    override fun hashCode(): Int {
        return orderId.hashCode()
    }
}
