package com.example.tip102group01friendzy.ui.feature.Memberpage

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

@Composable
fun Settingpage(
    navController: NavHostController
) {
// 狀態變數，用於管理各個可編輯內容的值
    var password by remember { mutableStateOf("**********") } // 預設密碼顯示為星號
    var name by remember { mutableStateOf("Angel Huang") } // 預設姓名值
    var nickname by remember { mutableStateOf("暱稱是這裡") } // 預設暱稱
    var phoneNumber by remember { mutableStateOf("+886 912345678") } // 預設手機號碼

    // 狀態變數，用於控制是否處於編輯模式
    var isEditingPassword by remember { mutableStateOf(false) } // 密碼是否處於編輯模式
    var isEditingName by remember { mutableStateOf(false) } // 姓名是否處於編輯模式
    var isEditingNickname by remember { mutableStateOf(false) } // 暱稱是否處於編輯模式
    var isEditingPhoneNumber by remember { mutableStateOf(false) } // 手機號碼是否處於編輯模式

    Column(
        modifier = Modifier
            .fillMaxSize() // 填滿可用空間
            .padding(16.dp) // 設置整體內邊距
    ) {
        // 返回按鈕與標題
        Row(
            verticalAlignment = Alignment.CenterVertically, // 垂直方向置中
            modifier = Modifier.fillMaxWidth() // 填滿寬度
        ) {
            IconButton(
                onClick = {
                    navController.navigate("memberscreen") // 返回 MemberScreen
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, // 使用返回箭頭圖標
                    contentDescription = "Back" // 無障礙描述文字
                )
            }
            Text(
                text = "\u8A2D\u5B9A", // 設定標題文字
                fontSize = 20.sp, // 字體大小
                fontWeight = FontWeight.Bold, // 粗體字
                modifier = Modifier.padding(start = 8.dp) // 與圖標間距
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) // 添加垂直間距

        // 會員帳號部分
        Text(text = "\u6703\u54E1\u5E33\u865F", fontWeight = FontWeight.Bold) // 顯示標籤文字
        Text(text = "angelhuang@cw.com.tw", modifier = Modifier.padding(vertical = 8.dp)) // 顯示帳號內容

        // 會員密碼部分
        EditableRow(
            label = "\u6703\u54E1\u5BC6\u78BC", // 密碼標籤
            value = password, // 顯示的密碼
            isEditing = isEditingPassword, // 是否處於編輯模式
            onEditClick = { isEditingPassword = true }, // 點擊進入編輯模式
            onSaveClick = { isEditingPassword = false }, // 點擊完成保存
            onValueChange = { password = it } // 更新密碼值
        )

        // 姓名部分
        EditableRow(
            label = "\u59D3\u540D", // 姓名標籤
            value = name, // 顯示的姓名
            isEditing = isEditingName, // 是否處於編輯模式
            onEditClick = { isEditingName = true }, // 點擊進入編輯模式
            onSaveClick = { isEditingName = false }, // 點擊完成保存
            onValueChange = { name = it } // 更新姓名值
        )

        // 暱稱部分
        EditableRow(
            label = "暱稱", // 暱稱標籤
            value = nickname, // 顯示的暱稱
            isEditing = isEditingNickname, // 是否處於編輯模式
            onEditClick = { isEditingNickname = true }, // 點擊進入編輯模式
            onSaveClick = { isEditingNickname = false }, // 點擊完成保存
            onValueChange = { nickname = it } // 更新暱稱值
        )

        // 手機號碼部分
        EditableRow(
            label = "\u624B\u6A5F\u865F\u78BC", // 手機號碼標籤
            value = phoneNumber, // 顯示的手機號碼
            isEditing = isEditingPhoneNumber, // 是否處於編輯模式
            onEditClick = { isEditingPhoneNumber = true }, // 點擊進入編輯模式
            onSaveClick = { isEditingPhoneNumber = false }, // 點擊完成保存
            onValueChange = { phoneNumber = it } // 更新手機號碼值
        )

        Spacer(modifier = Modifier.weight(1f)) // 添加一個可彈性填充的空間

        // 登出按鈕
        Button(
            onClick = {
                navController.navigate("loginscreen") // 跳轉到 LoginScreen
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), // 設置按鈕為透明背景
            modifier = Modifier.fillMaxWidth() // 按鈕填滿寬度
        ) {
            Text(text = "\u767B\u51FA\u5E33\u865F", color = Color.Red, fontWeight = FontWeight.Bold) // 顯示登出文字，紅色加粗
        }
    }
}

// 可編輯行的通用組件
@Composable
fun EditableRow(
    label: String, // 行標籤
    value: String, // 當前顯示的值
    isEditing: Boolean, // 是否處於編輯模式
    onEditClick: () -> Unit, // 進入編輯模式的回調
    onSaveClick: () -> Unit, // 保存內容的回調
    onValueChange: (String) -> Unit // 更新值的回調
) {
    Row(
        verticalAlignment = Alignment.CenterVertically, // 垂直方向置中
        modifier = Modifier
            .fillMaxWidth() // 填滿寬度
            .padding(vertical = 8.dp) // 添加上下內邊距
    ) {
        // 標籤與內容
        Column(modifier = Modifier.weight(1f)) { // 左側占據一部分空間
            Text(text = label, fontWeight = FontWeight.Bold) // 顯示標籤文字
            if (isEditing) {
                // 如果處於編輯模式，顯示文字輸入框
                TextField(
                    value = value, // 輸入框內的文字
                    onValueChange = onValueChange, // 更新文字的回調
                    modifier = Modifier.fillMaxWidth() // 輸入框填滿寬度
                )
            } else {
                // 非編輯模式顯示靜態文字
                Text(text = value, modifier = Modifier.padding(top = 4.dp)) // 添加頂部間距
            }
        }

        Spacer(modifier = Modifier.width(8.dp)) // 左右間距

        // 編輯或完成按鈕
        Button(
            onClick = {
                if (isEditing) {
                    onSaveClick() // 如果處於編輯模式，點擊保存
                } else {
                    onEditClick() // 否則進入編輯模式
                }
            }
        ) {
            Text(text = if (isEditing) "\u5B8C\u6210" else "\u7DE8\u8F2F") // 按鈕文字根據狀態切換
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SettingpagePreview(){
    Settingpage(rememberNavController())
}