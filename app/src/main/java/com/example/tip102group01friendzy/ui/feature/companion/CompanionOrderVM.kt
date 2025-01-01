package com.example.tip102group01friendzy.ui.feature.companion

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CompanionOrderVM :ViewModel(){
    private val _orderListState = MutableStateFlow(emptyList<CompanionOrder>())
    val orderListState = _orderListState.asStateFlow()

    private val _orderSelectState = MutableStateFlow(CompanionOrder())
    val orderSelectState = _orderSelectState.asStateFlow()
    fun setSelectOrder(order : CompanionOrder){
        _orderSelectState.value = order
    }

    init {
        _orderListState.value = fetchOrderList()
    }

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
}

data class CompanionOrder(
//    var orderID: Int,
//    var service_IDNO: Int,
//    var order_Person: String = "",
//    var order_Pirce:Double,
//    var order_Status: Int,
//    var order_content:String = "",
//    var reservation:  Boolean = false

    var orderID: Int = 0,//訂單編號
    var serviceID: Int = 0,//服務單號
    var orderPerson: String = "訂購人A",//訂購人
    var orderPoster: String ="刊登者A",//刊登者
    var orderPirce:Double = 0.0,//金額
    var orderStatus: Int = 0,//訂單狀態
    var orderTitle:String = "我是標題",//標題
    var orderContent:String = "我是內容123456",//內容
    var reservation:  Boolean = false,//暫時用 判斷預約的
    var startTime: String = "yyyy-MM-dd hh:mm",//開始時間
    var endTime: String = "yyyy-MM-dd hh:mm",//結束時間
    

){
    override fun equals(other: Any?): Boolean {
        return this.orderID == (other as CompanionOrder).orderID
    }

    override fun hashCode(): Int {
        return orderID.hashCode()
    }
}