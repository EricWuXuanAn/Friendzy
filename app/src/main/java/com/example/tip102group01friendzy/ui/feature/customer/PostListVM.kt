package com.example.tip102group01friendzy.ui.feature.customer

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PostListVM():ViewModel() {
    private val _postListState = MutableStateFlow(emptyList<PostList>())
    val postListState = _postListState.asStateFlow()

    init {
        _postListState.value = getPostInfo()
    }




    fun getPostInfo():List<PostList>{
        return listOf(
            PostList("01", "陪我打球 Badminton", "我今天很閒想打球但找不到人一起"),
            PostList("02", "逛SOGO Shooping", "我有選擇障礙 所以需要有人陪我一起幫我給建議"),
            PostList("03", "陪我看電影", "我想要有人陪我看電影"),
            PostList("04", "誠徵程式家教", "我要期中考拜託救救我"),
            PostList("05", "誠徵數學家教", "家裡小孩需要磨練"),
            PostList("06","誠徵陪跑員", "需要有人陪我跑步"),
            PostList("07", "誠徵羽球教練 Badminton Coach", "我想練羽球"),
            PostList("08", "一起吃飯 Dinner Together", "第一次來台北 沒朋友一起吃飯"),
            PostList("09", "出門踏青 Hang Out", "我想出去走走但又不想一個人"),
        )
    }
}