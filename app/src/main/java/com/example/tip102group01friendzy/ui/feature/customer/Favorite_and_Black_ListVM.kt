package com.example.tip102group01friendzy.ui.feature.customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tip102group01friendzy.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class Favorite_and_Black_ListVM():ViewModel() {
    //收集收藏名單資料
    private val _favoriteListState = MutableStateFlow(emptyList<Favorite_List>())
    val favoriteListState = _favoriteListState.asStateFlow()
   //收集黑名單資料
   private val _blackListState = MutableStateFlow(emptyList<Black_List>())
   val blackListState = _blackListState.asStateFlow()

   init {
       //CoroutineScope
       viewModelScope.launch {
           _blackListState.value = getBlacList()
           _favoriteListState.value = getfavList()
       }


   }
    //新增收藏名單的函式
    fun addFav(favList:Favorite_List){
        _favoriteListState.update {
            val newFav = it.toMutableList()
            newFav.add(favList)
            newFav
        }
    }
    //移除收藏名單的函式
    fun removeFav(favList:Favorite_List){
        _favoriteListState.update {
            val removeFav = it.toMutableList()
            removeFav.remove(favList)
            removeFav
        }
    }

    //新增黑名單的函式
    fun addBlackList(blackList:Black_List){
        _blackListState.update {
            val block = it.toMutableList()
            block.add(blackList)
            block
        }
    }

    //移除黑名單的函式


    //建立一個函式可以拿到收藏名單的資料
   suspend fun getfavList():List<Favorite_List>{
       try {
           val list = RetrofitInstance.api.showAllFavoriteList()
           return list
       }
       catch (e:Exception){
           return emptyList()
       }
   }

    //建立一個函式可以拿到黑名單資料
   suspend fun getBlacList(): List<Black_List>{
       try {
           val list = RetrofitInstance.api.showAllBlackList()
           return list
       }
       catch (e:Exception){
           return emptyList()
       }
   }

}