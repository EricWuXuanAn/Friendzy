package com.example.tip102group01friendzy.ui.feature.customer

import androidx.lifecycle.ViewModel
import com.example.tip102group01friendzy.RetrofitInstance

class ReservationConfirmVM():ViewModel() {

    suspend fun getSelectOrder(order_id: Int):OrderList{
//        Log.d("tag_", "order_idVM: $order_id")
        val order = RetrofitInstance.api.selectedorderList(
            selectedOrderListRequest(order_id = order_id)
        )
        return order
    }
}