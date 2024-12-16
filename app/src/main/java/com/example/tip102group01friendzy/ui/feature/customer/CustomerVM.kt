package com.example.tip102group01friendzy.ui.feature.customer

import androidx.lifecycle.ViewModel
import com.example.tip102group01friendzy.Customer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CustomerVM : ViewModel() {
    //_customer來獲取資料
    private val _customer = MutableStateFlow(emptyList<Customer>())

    //memberState回傳到view給使用者看
    val memberState = _customer.asStateFlow()

    init {
        _customer.value = getMemberInfo()
    }

    //新增會員功能
    fun addcustomer(member: Customer) {
        _customer.update {
            val customer = it.toMutableList()
            customer.add(member)
            customer
        }
    }

    //刪除會員功能
    fun removeCustomer(memner: Customer) {
        _customer.update {
            val customer = it.toMutableList()
            customer.remove(memner)
            customer
        }
    }


    fun getMemberInfo(): List<Customer> {
         return  listOf(
             Customer("毛安", "0978008840", "01", memberSpecialty = "羽球"),
             Customer("阿峻", "9876543219", "02", memberSpecialty = "電腦"),
             Customer("阿Miu", "0987654321", "03", memberSpecialty = "寫程式"),
             Customer("Nita", "0987654567", "04", memberSpecialty = "英語家教"),
             Customer("小宇", "09876542362", "05", memberSpecialty = "動漫")
         )
    }
}