package com.example.tip102group01friendzy.ui.feature.Memberpage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


// 主通知頁面
@Composable
fun Notices(navController: NavHostController) {
    // 假資料，用於顯示通知內容
    val notifications = listOf(
        Pair("訂單", "你的需求已有人應徵!"),
        Pair("訂單", "你的需求已有人應徵!"),
        Pair("關注", "你關注的服務已有人刊登!")
    )

    // 整體頁面布局
    Column(
        modifier = Modifier
            .fillMaxSize() // 填滿整個螢幕空間
            .background(Color.White) // 背景顏色為白色
            .padding(16.dp) // 設定整體內邊距
    ) {
        // 返回按鈕與標題部分
        Row(
            verticalAlignment = Alignment.CenterVertically, // 垂直方向置中
            modifier = Modifier.fillMaxWidth() // 水平方向填滿
        ) {
            // 返回按鈕
            IconButton(
                onClick = {
                    navController.popBackStack() // 點擊返回上一頁
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, // 使用 Material Design 的返回箭頭圖標
                    contentDescription = "Back" // 無障礙描述
                )
            }
            // 標題文字
            Text(
                text = "通知", // 顯示的標題文字
                fontSize = 20.sp, // 字體大小
                fontWeight = FontWeight.Bold, // 加粗
                modifier = Modifier.padding(start = 8.dp) // 與箭頭間的間距
            )
        }

        // 分隔線
        Divider(
            color = Color.Gray.copy(alpha = 0.5f), // 分隔線顏色為半透明灰色
            thickness = 1.dp, // 分隔線厚度
            modifier = Modifier.padding(vertical = 8.dp) // 與上下元素的間距
        )

        // 通知列表，依序顯示每條通知
        notifications.forEach { (title, content) ->
            NotificationItem(title = title, content = content) // 調用 NotificationItem 組件
        }
    }
}

// 單條通知項目組件
@Composable
fun NotificationItem(title: String, content: String) {
    // 每條通知的布局
    Column(
        modifier = Modifier
            .fillMaxWidth() // 填滿父元素寬度
            .padding(vertical = 8.dp) // 與上下通知的間距
            .background(
                color = Color(0xFFF9E6FF), // 背景顏色為淡紫色
                shape = RoundedCornerShape(8.dp) // 圓角背景
            )
            .padding(16.dp) // 內邊距，確保內容不緊貼邊緣
    ) {
        // 通知標題
        Text(
            text = title, // 顯示的標題
            fontWeight = FontWeight.Bold, // 標題加粗
            fontSize = 16.sp // 標題字體大小
        )
        Spacer(modifier = Modifier.height(4.dp)) // 標題與內容間的間距
        // 通知內容
        Text(
            text = content, // 顯示的通知內容
            fontSize = 14.sp, // 內容字體大小
            color = Color.DarkGray // 內容文字顏色
        )
    }
}

// 預覽組件，供開發時快速查看設計效果
@Preview(showBackground = true)
@Composable
fun PreviewNotificationPage() {
    Notices(navController = rememberNavController()) // 模擬一個 NavController
}