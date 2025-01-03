package com.example.tip102group01friendzy.ui.feature.customer

data class Black_List(
    var user_id: Int,
    var blacklist_id: Int,
//    var black_accountImg: Int = R.drawable.ic_launcher_foreground,
    var blacklist_reason: String
) {
//    override fun equals(other: Any?): Boolean {
//        return this.black_account == (other as Black_List).black_account
//    }
//
//    override fun hashCode(): Int {
//        return this.black_account.hashCode()
//    }
}