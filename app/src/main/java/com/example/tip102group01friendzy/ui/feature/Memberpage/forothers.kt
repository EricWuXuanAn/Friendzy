package com.example.tip102group01friendzy.ui.feature.Memberpage

import android.content.Context
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tip102group01friendzy.R
import com.example.tip102group01friendzy.Screen


@Composable
fun ForOthers(
    navController: NavHostController,
    forothersVM: forothersVM
) {
    // 狀態變數：控制收藏對話框是否顯示
    var showCollectDialog by remember { mutableStateOf(false) }
    // 狀態變數：控制黑名單對話框是否顯示
    var showBlacklistDialog by remember { mutableStateOf(false) }
// 觀察 ViewModel 的會員資料
    val memberInfo by forothersVM.memberInfo.collectAsState()
    var selfIntroduction by remember { mutableStateOf("") } // 自我介紹的最終內容



    val context = LocalContext.current
    LaunchedEffect(Unit) {
        // 初始化時從後端抓取會員資料
        forothersVM.fetchMemberInfo(context)
    }
    LaunchedEffect(memberInfo) {
        selfIntroduction = memberInfo.introduction ?: ""
    }

    // 使用 remember 和 mutableStateOf 來保持 savedCities 和 savedSpecialties 狀態
    val savedCities = remember { mutableStateOf<List<String>>(emptyList()) }
    val savedSpecialties = remember { mutableStateOf<List<String>>(emptyList()) }

// 使用 LaunchedEffect 來更新 savedCities 和 savedSpecialties 的值
    LaunchedEffect(context) {
        val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

        // 更新 savedCities
        val cities = preferences.getStringSet("selectedCities", emptySet())?.toList() ?: emptyList()
        savedCities.value = cities // 更新 savedCities 的值

        // 更新 savedSpecialties
        val specialties = preferences.getStringSet("selectedSpecialties", emptySet())?.toList() ?: emptyList()
        savedSpecialties.value = specialties // 更新 savedSpecialties 的值
    }
    // 整體布局
    Column(
        modifier = Modifier
            .fillMaxSize() // 填滿可用空間
            .verticalScroll(rememberScrollState()) // 支援垂直捲動
            .padding(16.dp) // 整體的外邊距
    ) {
        // 返回按鈕
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
        Row(
            verticalAlignment = Alignment.Top, // 讓頭像和評價區塊的頂部對齊
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

            Spacer(modifier = Modifier.width(16.dp)) // 頭像與評價區之間的水平間距

            // 名稱與編號
            Column(
                modifier = Modifier
                    .align(Alignment.Top) // 確保與頭像頂部對齊
                    .weight(1f) // 佔據中間剩餘空間
            ) {

            }

            // 評價區與按鈕
            Column(
                horizontalAlignment = Alignment.End, // 評價區和按鈕靠右對齊
                modifier = Modifier
                    .align(Alignment.Top) // 確保與頭像頂部對齊
            ) {
                // 評價區塊
                Column(
                    modifier = Modifier.align(Alignment.End) // 評價內容靠右對齊
                ) {
                    // 陪伴者評價
                    Text(
                        text = "陪伴者評價：${generateStars(memberInfo.companionScore)}",
                        fontSize = 14.sp,
                        color = Color.Black
                    )

                    // 顧客評價
                    Text(
                        text = "顧客評價：${generateStars(memberInfo.customerScore)}",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(8.dp)) // 評價與按鈕之間的空隙

                // 聊聊按鈕
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
                    Text(
                        text = "聊聊", // 按鈕文字
                        color = Color.Black, // 文字顏色
                        fontSize = 18.sp // 文字大小
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp)) // 增加空隙

        // 名稱
        Row(
            modifier = Modifier.fillMaxWidth(), // 填滿整行

            verticalAlignment = Alignment.CenterVertically, // 垂直居中
            horizontalArrangement = Arrangement.SpaceBetween // 左右間距
        ) {
            /// 名稱
            Row(
                modifier = Modifier.fillMaxWidth(), // 佔滿寬度
                verticalAlignment = Alignment.CenterVertically, // 垂直居中
                horizontalArrangement = Arrangement.SpaceBetween // 左右間距
            ) {
                // 名稱顯示
                // 顯示會員暱稱或名字與編號
                Text(
                    text = "${memberInfo.nickname?.takeIf { it.isNotBlank() } ?: memberInfo.name ?: ""}\nNo.${memberInfo.memberNo ?: ""}", // 暱稱或名字與編號
                    fontWeight = FontWeight.Bold, // 粗體字
                    fontSize = 20.sp, // 字體大小
                    modifier = Modifier.padding(vertical = 8.dp) // 上下間距
                )

            }


        }

        Spacer(modifier = Modifier.height(16.dp)) // 增加空隙

        Column(
            modifier = Modifier
                .fillMaxWidth() // 占滿父容器的寬度
                .padding(8.dp) // 整體內邊距為 8.dp
        ) {
            // 服務地區展示區
            Text(
                text = "服務地區", // 標題文字
                fontWeight = FontWeight.Bold, // 字體加粗
                modifier = Modifier.padding(bottom = 8.dp) // 與下面內容的間距
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth() // 占滿父容器的寬度
                    .clip(RoundedCornerShape(8.dp)) // 設定圓角樣式
                    .background(Color(0xFFDCD0F0)) // 設定背景顏色 (淡紫色)
                    .padding(16.dp) // 內邊距為 16.dp
            ) {
                if (savedCities.value.isEmpty()) {
                    // 若無服務地區資料，顯示提示文字
                    Text(
                        text = "尚未選擇服務地區", // 提示內容
                        color = Color.DarkGray // 顏色為深灰色
                    )
                } else {
                    // 顯示儲存的服務地區列表，用逗號分隔
                    Text(
                        text = savedCities.value.joinToString(", "), // 以逗號分隔的地區列表
                        color = Color.Black // 文字顏色為黑色
                    )
                }


            }

            Spacer(modifier = Modifier.height(16.dp)) // 增加 16.dp 的垂直間距

            // 專長展示區
            Text(
                text = "專長", // 標題文字
                fontWeight = FontWeight.Bold, // 字體加粗
                modifier = Modifier.padding(bottom = 8.dp) // 與下面內容的間距
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth() // 占滿父容器的寬度
                    .clip(RoundedCornerShape(8.dp)) // 設定圓角樣式
                    .background(Color(0xFFDCD0F0)) // 設定背景顏色 (淡紫色)
                    .padding(16.dp) // 內邊距為 16.dp
            ) {
                if (savedSpecialties.value.isEmpty()) {
                    // 若無專長資料，顯示提示文字
                    Text(
                        text = "尚未選擇專長", // 提示內容
                        color = Color.DarkGray // 顏色為深灰色
                    )
                } else {
                    // 顯示儲存的專長列表，用逗號分隔
                    Text(
                        text = savedSpecialties.value.joinToString(", "), // 以逗號分隔的專長列表
                        color = Color.Black // 文字顏色為黑色
                    )
                }
            }
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
                    .fillMaxWidth()
                    .background(Color(0xFFDCD0F0)) // 背景顏色設定為 #DCD0F0
                    .height(180.dp) // 設定高度
                    .padding(16.dp) // 內邊距
            ) {
                // 顯示自我介紹內容或提示文字
                Text(
                    text = if (!memberInfo.introduction.isNullOrBlank()) memberInfo.introduction!! else "尚未輸入自我介紹",
                    color = if (memberInfo.introduction.isNullOrBlank()) Color.DarkGray else Color.Black
                )

        }
    }
// 收藏與黑名單按鈕
    Row(
        horizontalArrangement = Arrangement.SpaceBetween, // 按鈕間距
        modifier = Modifier.fillMaxWidth() // 填滿整行
    ) {
        // 收藏按鈕
        Button(
            onClick = { showCollectDialog = true }, // 點擊觸發收藏對話框
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray), // 設定按鈕背景顏色
            modifier = Modifier
                .width(16.dp) // 按鈕寬度
                .height(16.dp) // 按鈕高度
        ) {
            Text(text = "收藏", color = Color.Black, fontSize = 18.sp) // 按鈕文字
        }

        // 黑名單按鈕
        Button(
            onClick = { showBlacklistDialog = true }, // 點擊觸發黑名單對話框
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray), // 設定按鈕背景顏色
            modifier = Modifier
                .width(16.dp) // 按鈕寬度
                .height(16.dp) // 按鈕高度
        ) {
            Text(text = "黑名單", color = Color.Black, fontSize = 18.sp) // 按鈕文字
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

@Preview(showBackground = true)
@Composable
fun ForOthersPreview() {
    val fakeForOthersVM = forothersVM() // 替換為你的實際 ViewModel
    val navController = rememberNavController() // 使用 NavHostController

    // 預覽 ForOthers 介面
    ForOthers(
        navController = navController,
        forothersVM = fakeForOthersVM
    )
}

