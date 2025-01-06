package com.example.tip102group01friendzy.ui.feature.Memberpage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.Screen
import com.example.tip102group01friendzy.TabVM


@Composable
fun ForOthers(
    navController: NavHostController,
    tabVM: TabVM
) {
    // 狀態變數：控制收藏對話框是否顯示
    var showCollectDialog by remember { mutableStateOf(false) }
    // 狀態變數：控制黑名單對話框是否顯示
    var showBlacklistDialog by remember { mutableStateOf(false) }

    // 整體布局
    Column(
        modifier = Modifier
            .fillMaxSize() // 填滿可用空間
            .verticalScroll(rememberScrollState()) // 支援垂直捲動
            .padding(16.dp) // 整體的外邊距
    ) {
        // 頭像與評價區域
        Row(
            verticalAlignment = Alignment.CenterVertically, // 垂直居中
            horizontalArrangement = Arrangement.SpaceBetween, // 元件之間間距均分
            modifier = Modifier.fillMaxWidth() // 填滿整行
        ) {
            // 頭像框
            Box(
                modifier = Modifier
                    .size(80.dp) // 設定頭像框大小
                    .clip(CircleShape) // 圖片裁剪為圓形
                    .background(Color.LightGray), // 背景顏色
                contentAlignment = Alignment.Center // 元件內容居中
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon), // 頭像資源
                    contentDescription = "Avatar", // 無障礙說明
                    tint = Color.DarkGray, // 圖標顏色
                    modifier = Modifier.size(40.dp) // 圖標大小
                )
            }

            // 評價區塊
            Column {
                Text(
                    text = "陪伴者評價 *****", // 陪伴者評價
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Text(
                    text = "顧客評價 *****", // 顧客評價
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp)) // 增加空隙

        // 名稱與功能按鈕
        Row(
            modifier = Modifier.fillMaxWidth(), // 填滿整行
            verticalAlignment = Alignment.CenterVertically, // 垂直居中
            horizontalArrangement = Arrangement.SpaceBetween // 左右間距
        ) {
            // 名稱與編號
            Text(
                text = "暱稱\nNo.123456", // 假資料
                fontWeight = FontWeight.Bold, // 粗體字
                fontSize = 20.sp // 字體大小
            )
            // 聊聊按鈕區塊
            // 聊聊按鈕
            Button(
                onClick = {
                    navController.navigate(Screen.ChatroomScreen.name) // 跳轉到聊聊頁面
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray), // 設定按鈕背景顏色
                modifier = Modifier.padding(horizontal = 8.dp) // 增加按鈕間距
            ) {
                // 圖標
                Image(
                    painter = painterResource(id = R.drawable.chat), // 自訂圖標資源
                    contentDescription = "Chat", // 無障礙說明
                    modifier = Modifier
                        .size(25.dp) // 設定圖標大小
                        .padding(end = 7.dp) // 圖標和文字間距
                )
                // 文字
                Text(
                    text = "聊聊", // 按鈕文字
                    color = Color.Black, // 文字顏色
                    fontSize = 18.sp // 文字大小
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp)) // 增加空隙

        // 服務地區區
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // 標題文字
            Text(
                text = "服務地區",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // 點擊展開或顯示選擇的服務地區
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFDCD0F0)) // 背景顏色設定為 #DCD0F0
                    .padding(16.dp) // 內邊距
            ) {
                Text(
                    text = "新北市、台北市",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp)) // 間距

            // 專長區
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                // 標題文字
                Text(
                    text = "專長",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // 點擊展開或顯示選擇的專長
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFDCD0F0)) // 背景顏色設定為 #DCD0F0
                        .padding(16.dp) // 內邊距
                ) {
                    Text(
                        text = "打籃球、打羽毛球",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }


                Spacer(modifier = Modifier.height(32.dp)) // 增加空隙

                // 自我介紹區域
                Column(
                    modifier = Modifier
                        .fillMaxWidth() // 填滿整行
                        .padding(8.dp) // 內邊距
                ) {
                    // 自我介紹標題
                    Text(
                        text = "自我介紹",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp) // 與內容的間距
                    )
                    // 自我介紹內容
                    Box(
                        modifier = Modifier
                            .fillMaxWidth() // 填滿整行
                            .height(120.dp) // 設定高度
                            .background(Color(0xFFDCD0F0)) // 背景顏色
                            .padding(16.dp) // 內邊距
                    ) {
                        Text(
                            text = "熱愛旅遊與美食，喜歡探索新事物並結交朋友。提供陪伴聊天與生活分享，讓每一天都充滿歡樂！", // 假資料
                            color = Color.Black,
                            fontSize = 20.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 收藏與黑名單按鈕
            Row(
                horizontalArrangement = Arrangement.SpaceBetween, // 按鈕間距
                modifier = Modifier.fillMaxWidth() // 填滿整行
            ) {
                // 收藏按鈕
                Button(
                    onClick = { showCollectDialog = true }, // 點擊觸發收藏對話框
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White) // 按鈕背景顏色
                ) {
                    Text(text = "收藏", color = Color.Black) // 按鈕文字
                }

                // 黑名單按鈕
                Button(
                    onClick = { showBlacklistDialog = true }, // 點擊觸發黑名單對話框
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White) // 按鈕背景顏色
                ) {
                    Text(text = "黑名單", color = Color.Black) // 按鈕文字
                }
            }
            // 收藏確認對話框
            if (showCollectDialog) {
                AlertDialog(
                    onDismissRequest = { showCollectDialog = false }, // 點擊背景關閉
                    title = { Text(text = "收藏") }, // 對話框標題
                    text = { Text(text = "是否將該用戶加入收藏？") }, // 對話框內容
                    confirmButton = {
                        Button(onClick = {
                            showCollectDialog = false
                            // 加入收藏邏輯
                        }) {
                            Text("確認")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showCollectDialog = false }) {
                            Text("取消")
                        }
                    }
                )
            }

            // 黑名單確認對話框
            if (showBlacklistDialog) {
                AlertDialog(
                    onDismissRequest = { showBlacklistDialog = false }, // 點擊背景關閉
                    title = { Text(text = "黑名單") }, // 對話框標題
                    text = { Text(text = "是否將該用戶加入黑名單？") }, // 對話框內容
                    confirmButton = {
                        Button(onClick = {
                            showBlacklistDialog = false
                            // 加入黑名單邏輯
                        }) {
                            Text("確認")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showBlacklistDialog = false }) {
                            Text("取消")
                        }
                    }
                )
            }

        }
    }
}


