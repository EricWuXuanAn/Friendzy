package com.example.tip102group01friendzy.ui.feature.companion

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CompanionMyPublishVM(): ViewModel() {
    private val _publishListState = MutableStateFlow(emptyList<MyPublish>())
    val publishListState = _publishListState.asStateFlow()

    init {
        _publishListState.value = fetchMyPublish()
    }

    private val _setMyPublishState = MutableStateFlow(MyPublish())
    val setMyPublishState = _setMyPublishState.asStateFlow()

    fun setMyPublish(myPublish:MyPublish){
        _setMyPublishState.value = myPublish
    }


    private fun fetchMyPublish() :List<MyPublish>{
        return listOf(
            MyPublish(1,"標題1","內容11111111","2024/01/11 14:00"),
            MyPublish(2,"標題2","內容22222222","2024/02/12 14:00"),
            MyPublish(3,"標題3","內容33333333","2024/03/13 14:00"),
            MyPublish(4,"標題4","內容44444444","2024/04/14 14:00"),
            MyPublish(5,"標題5","內容55555555","2024/05/15 14:00"),
            MyPublish(6,"標題6","內容66666666","2024/06/16 14:00"),
        )
    }

}

data class MyPublish(
    var serviceId: Int = 1,
    var serviceTitle: String = "我是標題",
    var serviceDetail: String = "我是內容123456",
    var startTime:String = "2000/01/01 00:00",
){
    override fun equals(other: Any?): Boolean {
        return this.serviceId == (other as MyPublish).serviceId
    }

    override fun hashCode(): Int {
        return this.serviceId.hashCode()
    }

}