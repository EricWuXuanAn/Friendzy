package com.example.tip102group01friendzy

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TabVM:ViewModel() {
    private val _tabBarVisibility = MutableStateFlow(true)
    val tabBarVisibility = _tabBarVisibility.asStateFlow()

    fun tabBarState(enable: Boolean){
        _tabBarVisibility.value = enable
    }

}