package com.example.tip102group01friendzy.ui.feature.companion

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.tip102group01friendzy.ui.feature.customer.OrderList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CompanionOrderVM :ViewModel(){
    private val _orderList = MutableStateFlow(emptyList<CompanionOrderList>())
    val orderList = _orderList.asStateFlow()

    init {
        _orderList.value = getOrderList()
    }

    fun getOrderList():List<CompanionOrderList>{
        return  listOf(
            CompanionOrderList(1, 1, "毛安", 300.0, 0, "一起打羽球"),
            CompanionOrderList(2, 3, "阿Miu", 200.0, 1,"教我寫程式"),
            CompanionOrderList(3, 2, "阿俊", 150.0, 2, "教我英文"),
            CompanionOrderList(4, 5, "Nita", 280.0, 0, "陪我去逛街"),
            CompanionOrderList(5, 4, "小宇", 400.0, 0, "陪我打電動"),
            CompanionOrderList(6, 1,"Ruby", 250.0, 0, "教我寫程式", true),
            CompanionOrderList(7, 2,"綸綸", 100.0, 0, "一起去跑步", true)
        )
    }
}

data class CompanionOrderList(
    var orderID: Int,
    var service_IDNO: Int,
    var order_Person: String = "",
    var order_Pirce:Double,
    var order_Status: Int,
    var order_content:String = "",
    var reservation:  Boolean = false
){
    override fun equals(other: Any?): Boolean {
        return this.orderID == (other as CompanionOrderList).orderID
    }

    override fun hashCode(): Int {
        return orderID.hashCode()
    }
}