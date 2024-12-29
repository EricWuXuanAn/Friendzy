package com.example.tip102group01friendzy.ui.feature.Memberpage

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.navigation.NavController
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.compose.rememberNavController


// 主通知頁面
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Notices(navController: NavController, notifications: List<Pair<String, String>>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("通知") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        // 主內容區域
        Column(
            modifier = Modifier
                .fillMaxSize() // 佔滿螢幕
                .padding(paddingValues) // 處理 Scaffold 提供的內邊距
        ) {
            notifications.forEach { (title, content) -> // 遍歷通知列表
                NotificationItem(title = title, content = content) // 顯示每條通知
            }
        }
    }
}

// 單條通知項目組件
// 單條通知項目
@Composable
fun NotificationItem(title: String, content: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth() // 占滿寬度
            .padding(8.dp) // 每條通知的外邊距
            .clickable { /* 可選操作，例如跳轉到詳情頁面 */ }
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFF0F5) // 背景顏色
            ),
            elevation = CardDefaults.cardElevation(4.dp) // 設置陰影
        ) {
            Column(
                modifier = Modifier.padding(16.dp) // 卡片內部填充
            ) {
                Text(
                    text = title, // 通知標題
                    fontWeight = FontWeight.Bold, // 粗體字
                    fontSize = 16.sp // 字體大小
                )
                Spacer(modifier = Modifier.height(4.dp)) // 標題與內容之間的間距
                Text(
                    text = content, // 通知內容
                    fontSize = 14.sp, // 字體大小
                    color = Color.Gray // 字體顏色
                )
            }
        }
    }
}


