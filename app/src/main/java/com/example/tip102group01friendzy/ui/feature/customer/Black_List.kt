package com.example.tip102group01friendzy.ui.feature.customer

data class Black_List(
    var user_id: Int,
    var blacklist_id: Int,
//    var black_accountImg: Int = R.drawable.ic_launcher_foreground,
    var blacklist_reason: String
)

data class requestDelete(
    val user_id: Int,
    val blacklist_id: Int
)