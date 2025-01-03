package com.example.tip102group01friendzy.ui.feature.customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tip102group01friendzy.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderVM:ViewModel() {
    private val _orderLis = MutableStateFlow(emptyList<OrderList>())
    val orderList:StateFlow<List<OrderList>> = _orderLis.asStateFlow()

    init {
        viewModelScope.launch {
            _orderLis.value = getOrderList()
        }
    }

    //新增訂單
    fun addorder(order:OrderList){
        _orderLis.update {
            val newOrder = it.toMutableList()
            newOrder.add(order)
            newOrder
        }
    }

      suspend fun getOrderList():List<OrderList>{
        try {
            val orderList = RetrofitInstance.api.showAllOrderList()
            return orderList
        }catch (e: Exception){
            return emptyList()
        }
    }


//    fun getOrderList():List<OrderList>{
//        return  listOf(
//            OrderList(1, 1, "毛安", 300.0, 0, "一起打羽球"),
//            OrderList(2, 3, "阿Miu", 200.0, 1,"教我寫程式"),
//            OrderList(3, 2, "阿俊", 150.0, 2, "教我英文"),
//            OrderList(4, 5, "Nita", 280.0, 0, "陪我去逛街"),
//            OrderList(5, 4, "小宇", 400.0, 0, "陪我打電動"),
//            OrderList(6, 1,"Ruby", 250.0, 0, "教我寫程式", true),
//            OrderList(7, 2,"綸綸", 100.0, 0, "一起去跑步", true)
//        )
//    }
}