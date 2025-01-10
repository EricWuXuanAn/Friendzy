package com.example.tip102group01friendzy.ui.feature.companion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tip102group01friendzy.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CompanionMyPublishVM(): ViewModel() {
    //專長
    private val _publishSkillState = MutableStateFlow(emptyList<ComSkill>())
    val publishSkillState = _publishSkillState.asStateFlow()

    //區域
    private val _publishAreaState = MutableStateFlow(emptyList<ComArea>())
    val publishAreaState = _publishAreaState.asStateFlow()

    private val _setMyPublishState = MutableStateFlow(MyPublish())
    val setMyPublishState = _setMyPublishState.asStateFlow()

    //回傳刊登資料
    fun setPublish(myPublish: MyPublish){
        viewModelScope.launch {
            _setMyPublishState.update { addServiceAndOrder(myPublish) }
        }
    }

    //取得地區
    fun getAreaState(){
        viewModelScope.launch {
            _publishAreaState.update { fetchAreaList() }
        }
    }
    //取得專長
    fun getSkillState(){
        viewModelScope.launch {
            _publishSkillState.update { fetchSkillList() }
        }
    }
    //API取得所有地區
    private suspend fun fetchAreaList(): List<ComArea>{
        try {
            val area = RetrofitInstance.api.comPublishArea()
            return area
        }catch (e:Exception){
            return emptyList()
        }
    }
    //API取得所有專長
    private suspend fun fetchSkillList(): List<ComSkill>{
        try {
            val skill = RetrofitInstance.api.comPublishSkill()
            return skill
        }catch (e:Exception){
            return emptyList()
        }
    }
    //用刊登資料接API新增服務、訂單各一筆
    private suspend fun  addServiceAndOrder(myPublish: MyPublish): MyPublish{
        try {
            val publish = RetrofitInstance.api.addPublish(myPublish)
            return publish
        }catch (e:Exception){
            return MyPublish()
        }
    }

/*
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
 */

}
//刊登需要：使用者編號、標題、內容、開始時間、結束時間、地區、金額
//預設：服務狀態&訂單狀態 = 0 ,金額 = 0.0f ,刊登者身分 = 1 ,
data class MyPublish(
    var memberNo: Int? = null,//使用者編號

    var title: String? = null,//標題
    var detail: String? = null,//內容
    var startTime: Long? = null,//開始時間
    var endTime: Long? = null,//開始時間
    var location: String? = null,//地區

    var priceval: Double = 0.0,//金額
    var serviceStatus: Int = 0,//服務狀態
    var orderStatus: Int = 0,//訂單狀態
    var posterStatus: Int = 1,//刊登者身分

){
}
//地區
data class ComArea(
    var areaNo: String? = null,//地區編號
    var areaCity: String? = null,//地區-城市
    var areaDistricy: String? = null,//地區-區

){
}
//專長
data class ComSkill(
    var expertiseNo: String? = null,//專長編號
    var expertiseLabel: String? = null,//專長
){
}