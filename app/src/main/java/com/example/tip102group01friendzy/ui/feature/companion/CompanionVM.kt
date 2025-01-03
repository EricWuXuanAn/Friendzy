package com.example.tip102group01friendzy.ui.feature.companion

import androidx.lifecycle.ViewModel
import com.example.tip102group01friendzy.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CompanionVM :ViewModel(){

    private val _companionState = MutableStateFlow(emptyList<Companion>())
    val companionState = _companionState.asStateFlow()


    init {
        _companionState.update { fetchCompanion() }
//        _companionState.value =  fetchCompanion()
    }

    private val _setCompanionState = MutableStateFlow(Companion())
    val setCompanionState = _setCompanionState.asStateFlow()

    fun setCompanion(companion: Companion){
        _setCompanionState.value = companion
    }
    private fun fetchCompanion() :List<Companion>{
        return listOf(
            /*
            Companion("001","名字01", R.drawable.friendzy,"需求項目01"),//刊登者編號、名字、頭像、服務項目
            Companion("002","名字02", R.drawable.friendzy,"需求項目02"),
            Companion("003","名字03", R.drawable.friendzy,"需求項目03"),
            Companion("004","名字04", R.drawable.friendzy,"需求項目04"),
            Companion("005","名字05", R.drawable.friendzy,"需求項目05"),
             */
            Companion(1,"名字01",true)
        )
    }

}