package com.example.tip102group01friendzy.ui.feature.customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tip102group01friendzy.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrderVM:ViewModel() {
    private val _orderLis = MutableStateFlow(emptyList<OrderList>())
    val orderList:StateFlow<List<OrderList>> = _orderLis.asStateFlow()

    init {
        viewModelScope.launch {
            _orderLis.value = getOrderList()
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



}