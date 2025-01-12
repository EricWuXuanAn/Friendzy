package com.example.tip102group01friendzy.ui.feature.search
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tip102group01friendzy.RetrofitInstance
import com.example.tip102group01friendzy.ui.feature.customer.Black_List
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchVM: ViewModel () {

    //陪伴者經緯度,自介等資料
    private val _MapMarkerDataState = MutableStateFlow(emptyList<CompanionInfo>())
    val MapMarkerDataState = _MapMarkerDataState.asStateFlow()

    init {
        //CoroutineScope
        viewModelScope.launch {
            _MapMarkerDataState.value = getMapMarkerDataList()
        }
    }

    //建立一個函式可以拿到陪伴者經緯度,自介等資料
    suspend fun getMapMarkerDataList(): List<CompanionInfo>{
        try {
            val list = MapApiInstance.api.showCompanionInfo()
            return list
        }
        catch (e:Exception){
            return emptyList()
        }
    }
}