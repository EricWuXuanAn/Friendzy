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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import com.example.tip102group01friendzy.TabVM


@Composable
fun MemberScreen(
    navController: NavHostController,
    tabVM: TabVM
) {
    val context = LocalContext.current

    // 使用 remember 保存輸入框的狀態（專長與自我介紹）
    // 專長選項列表與已選專長的狀態
    val specialties = listOf(
        "專長1",
        "專長2",
        "專長3",
        "專長4",
        "專長5",
        "專長6",
        "專長7",
        "專長8",
        "專長9",
        "專長10"
    )
    val selectedSpecialties = remember { mutableStateListOf<String>() } // 動態儲存使用者選擇的專長
    var isSpecialtiesExpanded by remember { mutableStateOf(false) } // 控制專長是否展開的狀態

    // 城市選項列表與已選城市的狀態
    val cities = listOf(
        "台北市", "新北市", "基隆市", "桃園市", "新竹市", "新竹縣", "苗栗縣",
        "台中市", "彰化縣", "南投縣", "雲林縣", "嘉義市", "嘉義縣", "台南市",
        "高雄市", "屏東縣", "宜蘭縣", "花蓮縣", "台東縣", "澎湖縣", "金門縣", "連江縣"
    )
    val selectedCities = remember { mutableStateListOf<String>() } // 動態儲存使用者選擇的城市
    var isCitiesExpanded by remember { mutableStateOf(false) } // 控制服務地區是否展開的狀態
    // 暫存選擇項目
    val tempSelectedCities = remember { mutableStateListOf<String>() }
    // 用於暫時存儲用戶在服務地區中勾選的地區，未按下確認按鈕時不影響主列表。

    val tempSelectedSpecialties = remember { mutableStateListOf<String>() }
    // 用於暫時存儲用戶在專長區中勾選的專長，未按下確認按鈕時不影響主列表。

    // 是否處於編輯自我介紹的狀態
    var isEditingSelfIntroduction by remember { mutableStateOf(false) }

    // 暫存的自我介紹內容
    var tempSelfIntroduction by remember { mutableStateOf("") }

    // 自我介紹的最終內容
    var selfIntroduction by remember { mutableStateOf("") }

    // 計算平均評價（未來可透過其他頁面傳入評價資料）
    val averageRating by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // 支援垂直捲動
            .padding(16.dp) // 外邊距
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, // 垂直居中對齊
            horizontalArrangement = Arrangement.SpaceBetween, // 元件之間保持間距
            modifier = Modifier.fillMaxWidth()
        ) {
            // 頭像
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon),
                    contentDescription = "Avatar",
                    tint = Color.DarkGray,
                    modifier = Modifier.size(40.dp)
                )
            }
            // 評價區 - 平均評價
            Column {
                Text(
                    text = "陪伴者評價", //${generateStars(averageRating)}",
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Text(
                    text = "顧客評價", //${generateStars(averageRating)}",
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
            // 設定按鈕
            IconButton(onClick = {
                navController.navigate(Screen.SettingScreen.name) // 跳轉到設定頁面
            }) {
                // 使用自訂圖標替換預設 Icons.Default.Settings
                Image(
                    painter = painterResource(id = R.drawable.set), // 自訂圖標
                    contentDescription = "Settings",
                    modifier = Modifier.size(24.dp), // 設定圖標大
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        /// 名稱與聊聊按鈕
        Row(
            modifier = Modifier.fillMaxWidth(), // 佔滿寬度
            verticalAlignment = Alignment.CenterVertically, // 垂直居中
            horizontalArrangement = Arrangement.SpaceBetween // 左右間距
        ) {
            // 名稱顯示
            Text(
                text = "暱稱\nNo.123456", // 名稱與編號
                fontWeight = FontWeight.Bold, // 粗體字
                fontSize = 20.sp // 字體大小
            )

        }

        Spacer(modifier = Modifier.height(16.dp))



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
                    .clickable { isCitiesExpanded = true } // 點擊展開選單
                    .padding(16.dp) // 內邊距
            ) {
                if (selectedCities.isEmpty()) {
                    // 若未選擇任何服務地區，顯示提示文字
                    Text(
                        text = "請選擇服務地區",
                        color = Color.DarkGray
                    )
                } else {
                    // 顯示已選擇的服務地區
                    Text(
                        text = selectedCities.joinToString(", ")
                    )
                }
            }

            if (isCitiesExpanded) {
                // 展開服務地區選單
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFDCD0F0)) // 背景顏色設定為 #DCD0F0
                        .padding(8.dp)
                ) {
                    // 將服務地區分為多行排列，每行最多 3 個
                    cities.chunked(3).forEach { rowCities ->
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            rowCities.forEach { city ->
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    // Checkbox 用於選擇或取消選擇服務地區
                                    Checkbox(
                                        checked = city in tempSelectedCities,
                                        onCheckedChange = { isChecked ->
                                            if (isChecked) {
                                                tempSelectedCities.add(city) // 勾選後新增至暫存清單
                                            } else {
                                                tempSelectedCities.remove(city) // 取消勾選後移出暫存清單
                                            }
                                        }
                                    )
                                    // 顯示地區名稱
                                    Text(text = city)
                                }
                            }
                        }
                    }

                    // 確認按鈕
                    Button(
                        onClick = {
                            selectedCities.clear()
                            selectedCities.addAll(tempSelectedCities) // 將暫存清單的內容儲存為選擇結果
                            isCitiesExpanded = false // 摺疊選單
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("確認")
                    }
                }
            }
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
                    .clickable { isSpecialtiesExpanded = true } // 點擊展開選單
                    .padding(16.dp) // 內邊距
            ) {
                if (selectedSpecialties.isEmpty()) {
                    // 若未選擇任何專長，顯示提示文字
                    Text(
                        text = "請選擇專長",
                        color = Color.DarkGray
                    )
                } else {
                    // 顯示已選擇的專長
                    Text(
                        text = selectedSpecialties.joinToString(", ")
                    )
                }
            }

            if (isSpecialtiesExpanded) {
                // 展開專長選單
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFDCD0F0)) // 背景顏色設定為 #DCD0F0
                        .padding(8.dp)
                ) {
                    // 將專長分為多行排列，每行最多 3 個
                    specialties.chunked(3).forEach { rowSpecialties ->
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            rowSpecialties.forEach { specialty ->
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    // Checkbox 用於選擇或取消選擇專長
                                    Checkbox(
                                        checked = specialty in tempSelectedSpecialties,
                                        onCheckedChange = { isChecked ->
                                            if (isChecked) {
                                                tempSelectedSpecialties.add(specialty) // 勾選後新增至暫存清單
                                            } else {
                                                tempSelectedSpecialties.remove(specialty) // 取消勾選後移出暫存清單
                                            }
                                        }
                                    )
                                    // 顯示專長名稱
                                    Text(text = specialty)
                                }
                            }
                        }
                    }

                    // 確認按鈕
                    Button(
                        onClick = {
                            selectedSpecialties.clear()
                            selectedSpecialties.addAll(tempSelectedSpecialties) // 將暫存清單的內容儲存為選擇結果
                            isSpecialtiesExpanded = false // 摺疊選單
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("確認")
                    }
                }
            }
        }

    Spacer(modifier = Modifier.height(16.dp))


        // 自我介紹區塊
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // 標題
            Text(
                text = "自我介紹",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // 判斷是否正在編輯
            if (isEditingSelfIntroduction) {
                // 編輯模式
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFDCD0F0)) // 背景顏色設定為 #DCD0F0
                        .padding(8.dp)
                ) {
                    // 自我介紹輸入框
                    TextField(
                        value = tempSelfIntroduction, // 暫存輸入內容
                        onValueChange = { tempSelfIntroduction = it }, // 更新暫存內容
                        placeholder = { Text("請輸入您的自我介紹") }, // 提示文字
                        modifier = Modifier
                            .fillMaxWidth() // 佔滿寬度
                            .height(120.dp) // 設定高度
                    )

                    // 確認按鈕
                    Button(
                        onClick = {
                            selfIntroduction = tempSelfIntroduction // 更新自我介紹內容
                            isEditingSelfIntroduction = false // 結束編輯模式
                        },
                        modifier = Modifier.align(Alignment.End) // 按鈕靠右
                    ) {
                        Text("確認")
                    }
                }
            } else {
                // 顯示模式
                Box(modifier = Modifier
                    .fillMaxWidth() // 填滿整行
                    .height(120.dp) // 設定高度
                    .background(Color(0xFFDCD0F0)) // 背景顏色
                    .padding(16.dp) // 內邊距
                ) {
                    // 顯示自我介紹內容或提示文字
                    Text(
                        text = if (selfIntroduction.isEmpty()) "點擊編輯您的自我介紹" else selfIntroduction,
                        color = if (selfIntroduction.isEmpty()) Color.DarkGray else Color.Black
                    )
                }
            }
        }



    // 增加間距，讓內容向上排列
    Spacer(modifier = Modifier.weight(1f))
    }
}


@Composable
@Preview(showBackground = true)
fun memberScreenPreview() {
    MemberScreen(rememberNavController(), tabVM = TabVM())
}