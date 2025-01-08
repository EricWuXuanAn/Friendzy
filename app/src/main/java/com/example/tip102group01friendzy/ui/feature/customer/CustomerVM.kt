package com.example.tip102group01friendzy.ui.feature.customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tip102group01friendzy.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CustomerVM : ViewModel() {
    //_customer來獲取資料
    private val _recommend = MutableStateFlow(emptyList<Post>())

    //memberState回傳到view給使用者看
    val recommendPostListState = _recommend.asStateFlow()

    init {
        viewModelScope.launch {
            _recommend.value = getPostList()
        }
    }

//    //新增會員功能
//    fun addcustomer(post: Post) {
//        _recommend.update {
//            val customer = it.toMutableList()
//            customer.add(post)
//            customer
//        }
//    }
//
//    //刪除會員功能
//    fun removeCustomer(memner: Customer) {
//        _customer.update {
//            val customer = it.toMutableList()
//            customer.remove(memner)
//            customer
//        }
//    }



    suspend fun getPostList():List<Post>{
        try {
            val list = RetrofitInstance.api.showPostList()
            return list
        }catch (e:Exception){
            return emptyList()
        }
    }
}