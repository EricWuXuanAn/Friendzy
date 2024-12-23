package com.example.tip102group01friendzy.ui.feature.customer

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class Favorite_and_Black_ListVM():ViewModel() {
    //收集收藏名單資料
    private val _favoriteListState = MutableStateFlow(emptyList<Favorite_List>())
    val favoriteListState = _favoriteListState.asStateFlow()
   //收集黑名單資料
   private val _blackListState = MutableStateFlow(emptyList<Black_List>())
   val blackListState = _blackListState.asStateFlow()

   init {
       _favoriteListState.value = getfavListInfo()
       _blackListState.value = getBlackListInfo()
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
    fun removeBlackList(blackList:Black_List){
        _blackListState.update {
            val block = it.toMutableList()
            block.remove(blackList)
            block
        }
    }


    //建立一個函式可以拿到收藏名單的資料
    fun getfavListInfo():List<Favorite_List>{
        return listOf(
            Favorite_List(1, "毛安", 3),
            Favorite_List(2, "阿俊", 1),
            Favorite_List(3, "阿MIU",  5),
            Favorite_List(4, "Nita", 2)
        )
    }

    //建立一個函式可以拿到黑名單資料
    fun getBlackListInfo():List<Black_List>{
        return listOf(
            Black_List(3, 2, content = "遲到且服務態度不佳"),
            Black_List(1,5, content = "服務與公告程度不符"),
            Black_List(4, 1, content = "服務期間不告知就早退")
        )
    }

}